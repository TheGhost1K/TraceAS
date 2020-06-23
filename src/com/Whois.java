package com;

import java.net.*;
import java.io.*;

class Whois {
    public static String getWhoisRipe(String address) throws Exception {
        int number;
        StringBuilder tempRes = new StringBuilder();
        try (Socket s = new Socket("whois.ripe.net", 43)) {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            String query = ("" + address + "\n");
            byte buffer[] = query.getBytes();
            out.write(buffer);
            while ((number = in.read()) != -1) {
                tempRes.append((char) number);
            }
        }
        String queryResult = tempRes.toString();
        if (queryResult.contains("IPv4 address block not managed by the RIPE NCC"))
            return "-1";
        return RegexWorker.findAS(queryResult);
    }

    public static String getWhoisArin(String address) throws Exception {
        int number;
        StringBuilder tempRes = new StringBuilder();
        try (Socket socket = new Socket("whois.arin.net", 43)) {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            String query = ("z + " + address + "\n");
            byte buffer[] = query.getBytes();
            out.write(buffer);
            while ((number = in.read()) != -1) {
                tempRes.append((char) number);
            }
        }
        String queryResult = tempRes.toString();
        if (queryResult.contains("These addresses have been further assigned to users in")
                || queryResult.contains("These addresses are in use by many millions")
                || queryResult.contains("APNIC") || queryResult.contains("AfriNIC")
                || queryResult.contains("LACNIC"))
            return "-1";
        return RegexWorker.findAS(queryResult);
    }
}
