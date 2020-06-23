package com;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexWorker {
    public static String findAS (String input)
    {
        String regex = "(as[0-9]+)|(AS[0-9]+)";
        List<String> resList = new LinkedList<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            while (matcher.find(matcher.start() + 1)) {
                resList.add(matcher.group());
            }
        }
        if (resList.isEmpty()) return "";
        return ((LinkedList<String>) resList).getFirst();
    }
}
