"""Offline original-reader tests; generated documents only, no model or database calls."""
import hashlib
import importlib.util
import tempfile
import unittest
from pathlib import Path

import pymupdf

ROOT = Path(__file__).resolve().parents[2]
spec = importlib.util.spec_from_file_location('original_reader', ROOT / 'scripts/digital_worker_original.py')
reader = importlib.util.module_from_spec(spec)
spec.loader.exec_module(reader)


class OriginalReaderChecks(unittest.TestCase):
    def fixture(self, directory, scanned=False):
        file = Path(directory) / 'synthetic.pdf'
        with pymupdf.open() as doc:
            page = doc.new_page()
            if not scanned:
                page.insert_text((40, 50), 'SYNTHETIC original evidence, not clinical data. ' * 3)
                page.insert_text((40, 80), 'Phone: 17800000000 ID: 110101190001010000')
            doc.save(file)
        return {'id': 'fixture-source', 'name': 'synthetic.pdf', 'path': str(file), 'pageCount': 1, 'sha256': hashlib.sha256(file.read_bytes()).hexdigest()}

    def test_verbatim_native_text_and_hash(self):
        with tempfile.TemporaryDirectory() as directory:
            source = self.fixture(directory)
            result = reader.read_sources({'files': [source]})
            self.assertEqual(result['sources'][0]['sha256'], source['sha256'])
            self.assertIn('SYNTHETIC', result['pages'][0]['text'])
            self.assertNotIn('image', result['pages'][0])

    def test_contact_and_id_redaction(self):
        with tempfile.TemporaryDirectory() as directory:
            result = reader.read_sources({'files': [self.fixture(directory)]})
            self.assertNotIn('17800000000', result['pages'][0]['text'])
            self.assertNotIn('110101190001010000', result['pages'][0]['text'])

    def test_changed_original_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            source = self.fixture(directory)
            source['sha256'] = '0' * 64
            with self.assertRaisesRegex(ValueError, 'ORIGINAL_HASH_MISMATCH'):
                reader.read_sources({'files': [source]})

    def test_missing_original_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            source = self.fixture(directory)
            source['path'] += '.missing'
            with self.assertRaisesRegex(ValueError, 'ORIGINAL_UNAVAILABLE'):
                reader.read_sources({'files': [source]})

    def test_page_count_mismatch_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            source = self.fixture(directory)
            source['pageCount'] = 2
            with self.assertRaisesRegex(ValueError, 'ORIGINAL_PAGE_MISMATCH'):
                reader.read_sources({'files': [source]})

    def test_scanned_page_rendered_for_vision(self):
        with tempfile.TemporaryDirectory() as directory:
            result = reader.read_sources({'files': [self.fixture(directory, scanned=True)]})
            self.assertTrue(result['pages'][0]['image'])
            self.assertEqual(result['pages'][0]['page'], 1)


if __name__ == '__main__':
    unittest.main()
