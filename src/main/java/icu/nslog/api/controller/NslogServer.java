package icu.nslog.api.controller;

import icu.nslog.exception.NslogServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO nslog server rest api
public class NslogServer {

    public static void dnlogServerStart() throws IOException, NslogServerException {
        String s2 = "python3 nslog.py";
        Process process = Runtime.getRuntime().exec(s2);
        BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String resultLine;
        StringBuilder lines = new StringBuilder();
        while ((resultLine = stream.readLine()) != null) {
            if(!resultLine.contains("dns server up")){
                throw new NslogServerException("dns server start error: " + resultLine);
            }
        }
        while ((resultLine = errorStream.readLine()) != null) {
            lines.append(resultLine).append("\n");
        }
        stream.close();
        if (lines.length() != 0){
            throw new NslogServerException(lines.toString());
        }

    }
}
