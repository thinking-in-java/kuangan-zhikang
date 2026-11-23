"""Read only the hash-verified originals supplied by the scoped Java service.

No database access or network requests. Native text stays verbatim; scanned pages
are rendered for the same vision API used by OCR. Never print document data to stderr.
"""
import base64
import hashlib
import json
import re
import sys
from pathlib import Path

import pymupdf


def read_sources(request):
    pages, sources = [], []
    for source in request['files']:
        path = Path(source['path'])
        if not path.is_file() or path.stat().st_size > 30 * 1024 * 1024:
            raise ValueError('ORIGINAL_UNAVAILABLE')
        raw = path.read_bytes()
        digest = hashlib.sha256(raw).hexdigest()
        if digest != source['sha256']:
            raise ValueError('ORIGINAL_HASH_MISMATCH')
        with pymupdf.open(path) as doc:
            if doc.needs_pass or doc.page_count != source['pageCount'] or doc.page_count > 40:
                raise ValueError('ORIGINAL_PAGE_MISMATCH')
            if len(pages) + doc.page_count > 40:
                raise ValueError('ORIGINAL_PAGE_LIMIT')
            for i, page in enumerate(doc):
                text = page.get_text(sort=True)
                text = re.sub(r'(?<!\d)1[3-9]\d{9}(?!\d)', '[联系方式已隐去]', text)
                text = re.sub(r'(?<!\d)\d{17}[0-9Xx](?!\d)', '[证件号码已隐去]', text)
                item = {'sourceId': source['id'], 'page': i + 1, 'text': text}
                # Preserve readable originals, including layout text used to verify quotes.
                if sum(c.isalnum() for c in text) < 60:
                    scale = min(140 / 72, 1800 / max(page.rect.width, page.rect.height))
                    item['image'] = base64.b64encode(page.get_pixmap(matrix=pymupdf.Matrix(scale, scale), alpha=False).tobytes('png')).decode('ascii')
                pages.append(item)
            sources.append({'id': source['id'], 'name': source['name'], 'sha256': digest, 'pages': doc.page_count})
    return {'sources': sources, 'pages': pages}


if __name__ == '__main__':
    try:
        json.dump(read_sources(json.load(sys.stdin)), sys.stdout, ensure_ascii=True)
    except Exception as error:
        code = str(error) if isinstance(error, ValueError) and str(error).startswith('ORIGINAL_') else 'ORIGINAL_READ_FAILED'
        json.dump({'error': code}, sys.stdout)
        sys.exit(1)
