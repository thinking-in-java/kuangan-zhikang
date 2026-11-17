"""B-side helper: bind a standalone 2.0.0 result to an authenticated A task.

Only call with input fetched using that task's valid worker lease. A validates
the result independently. Patient values, evidence IDs and clinical results
are not rewritten. No file is imported or posted by this module.
"""
from copy import deepcopy

def bind_to_task(result, task_input):
    if result.get('contract_version') != '2.0.0' or task_input.get('contract_version', task_input.get('contractVersion')) != '2.0.0':
        raise ValueError('Contract version mismatch; do not convert semantics automatically')
    if result.get('dictionary_version', '2.0.0') != task_input.get('dictionary_version', '2.0.0'):
        raise ValueError('Dictionary version mismatch')
    source_files = task_input.get('source_files')
    if not isinstance(source_files, list) or not source_files:
        raise ValueError('A task input must include source_files with SHA256 and page count')
    by_hash = {str(f['sha256']).lower(): f for f in source_files}
    if len(by_hash) != len(source_files):
        raise ValueError('Ambiguous A source files')
    out = deepcopy(result)
    mapping = {}
    for f in out.get('source_files', []):
        target = by_hash.get(str(f['sha256']).lower())
        if target is None or f['page_count'] != target['page_count']:
            raise ValueError('Result source does not match the claimed task file')
        old = f['source_file_id']
        if old in mapping:
            raise ValueError('Duplicate result source file')
        mapping[old] = target['source_file_id']
        f.update(source_file_id=target['source_file_id'], sha256=target['sha256'], page_count=target['page_count'])
    if len(mapping) != len(source_files) or len(set(mapping.values())) != len(source_files):
        raise ValueError('Result must cover exactly the task source files')
    context = {
        'contract_version': '2.0.0',
        'scope_code': task_input['scope_code'], 'data_mode': task_input['data_mode'],
        'report_id': task_input.get('report_id', task_input.get('reportId')),
        'revision': task_input['revision']
    }
    if not context['report_id']:
        raise ValueError('Missing A report ID')
    out.update(context)
    out['record_id'] = task_input['record_id']
    out['prior_revision'] = task_input.get('prior_revision')
    for key in ['items', 'conclusions', 'advice']:
        for row in out[key]:
            row.update(context)
    for evidence in out['evidence']:
        old = evidence['source_file_id']
        if old not in mapping:
            raise ValueError('Evidence refers to a different source')
        evidence['source_file_id'] = mapping[old]
    # report_kind is B's detection result. Preserve unknown for doctor review.
    return out
