package com.yihe.web.controller.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClearBufferThread implements Runnable {
    private InputStream inputStream;

    public ClearBufferThread(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (br.readLine() != null)
                ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}