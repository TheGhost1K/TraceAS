package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPWorker {
    private final String USER_AGENT = "Mozilla/5.0";

    protected String sendGet(String ip) throws Exception {
        String url = "https://ipinfo.io/"+ ip + "/json"+"?token=9f9a1845e234fc";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("ip") || inputLine.contains("org")) {
                String tempstr = inputLine.replaceAll("\"","");
                String out = tempstr.split(": ")[1];
                response.append(out);
            }
        }
        in.close();
        return response.toString();
    }
}
