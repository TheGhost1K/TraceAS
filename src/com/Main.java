package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception {
        String domainAddress = "yandex.ru";
        HTTPWorker http = new HTTPWorker();
        String out;
        StringBuilder outBuilder = new StringBuilder();

        for (String string : getIpList(domainAddress))
        {
            String[] tempArr = http.sendGet(string).split(",");
            if(tempArr.length == 1)
                outBuilder.append(String.format("%-30s%n",tempArr[0]));
            if(tempArr.length == 2)
                outBuilder.append(String.format("%-30s%-10s%n",tempArr[0],tempArr[1].split(" ")[0]));
        }
        out = outBuilder.toString();
        System.out.printf("%-30s%-10s%n","ip address","Номер АС");
        System.out.println("-----------------------------------------");
        System.out.println(out);
    }

    public static List<String> getIpList(String ip_domain) {
        BufferedReader in;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("tracert " + ip_domain);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            String regexp = "([0-9]{1,3}[.]){3}[0-9]{1,3}";
            List<String> allMatches = new ArrayList<>();
            Pattern pattern = Pattern.compile(regexp);
            if (process == null)
                System.out.println("could not connect");
            while ((line = in.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    allMatches.add(matcher.group());
                }
            }
            allMatches.remove(0);
            return allMatches;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
