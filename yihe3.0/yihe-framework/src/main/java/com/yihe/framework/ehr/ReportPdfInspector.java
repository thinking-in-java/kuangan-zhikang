package com.yihe.framework.ehr;

import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;
import java.nio.file.Files;
import java.nio.file.Path;

/** Read upload metadata without modifying the original or keeping a file handle open. */
final class ReportPdfInspector {
    private ReportPdfInspector() {}

    static int pageCount(Path source) {
        PdfReader pdf = null;
        try {
            // Uploads are capped at 20 MiB before this call. A byte-backed reader also
            // lets Windows delete the staging file if the PDF constructor fails.
            pdf = new PdfReader(Files.readAllBytes(source));
            // An owner-permission PDF may be encrypted but open without a password.
            // We only inspect it; never set unethicalreading or rewrite/decrypt it.
            int pages = pdf.getNumberOfPages();
            if (pages < 1 || pages > 1000) {
                throw new EhrException(422, "PDF页数应为1至1000页");
            }
            return pages;
        } catch (BadPasswordException e) {
            throw new EhrException(422, "PDF需要打开密码，请上传无需打开密码的报告原件");
        } catch (EhrException e) {
            throw e;
        } catch (Exception e) {
            throw new EhrException(422, "PDF损坏或不可读取，请检查报告文件");
        } finally {
            if (pdf != null) pdf.close();
        }
    }
}
