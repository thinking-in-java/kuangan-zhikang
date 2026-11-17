package com.yihe.framework.ehr;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;

/** Parse the actual failed upload without changing its bytes or creating database rows. */
public final class ReportPdfInspectorChecks {
    public static void main(String[] args) throws Exception {
        Path original = Paths.get(args[0]);
        byte[] before = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(original));
        int pages = ReportPdfInspector.pageCount(original);
        if (pages != 11) throw new AssertionError("Actual PDF page count: " + pages);
        byte[] after = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(original));
        if (!Arrays.equals(before, after)) throw new AssertionError("Original was modified");
        Path stage = Files.createTempFile("pdf-constructor-failure-", ".bin");
        Files.write(stage, "%PDF-1.7\ninvalid".getBytes("US-ASCII"));
        try {
            try {
                ReportPdfInspector.pageCount(stage);
                throw new AssertionError("Malformed PDF accepted");
            } catch (EhrException e) {
                if (e.status != 422) throw e;
            }
        } finally {
            Files.delete(stage); // Fails on Windows if parser leaked a file handle.
        }
        System.out.println("PASS actual failed PDF: 11 pages, original SHA-256 unchanged");
        System.out.println("PASS malformed PDF: 422 and staging file immediately deletable");
        System.out.println("Crypto provider: " + org.bouncycastle.asn1.ASN1Primitive.class
                .getProtectionDomain().getCodeSource().getLocation().getPath());
    }
}
