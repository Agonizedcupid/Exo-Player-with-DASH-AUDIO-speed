package com.udvash.videofolder.pipe;

import static com.udvash.videofolder.pipe.Utils.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Parser() {
    }

    public static class RegexException extends ParsingException {
        public RegexException(String message) {
            super(message);
        }
    }

    public static String matchGroup1(String pattern, String input) throws RegexException {
        return matchGroup(pattern, input, 1);
    }

    public static String matchGroup1(Pattern pattern, String input) throws RegexException {
        return matchGroup(pattern, input, 1);
    }

    public static String matchGroup(String pattern, String input, int group) throws RegexException {
        Pattern pat = Pattern.compile(pattern);
        return matchGroup(pat, input, group);
    }

    public static String matchGroup(Pattern pat, String input, int group) throws RegexException {
        Matcher mat = pat.matcher(input);
        boolean foundMatch = mat.find();
        if (foundMatch) {
            return mat.group(group);
        } else {
            // only pass input to exception message when it is not too long
            if (input.length() > 1024) {
                throw new RegexException("failed to find pattern \"" + pat.pattern() + "\"");
            } else {
                throw new RegexException("failed to find pattern \"" + pat.pattern() + "\" inside of \"" + input + "\"");
            }
        }
    }

    public static boolean isMatch(String pattern, String input) {
        final Pattern pat = Pattern.compile(pattern);
        final Matcher mat = pat.matcher(input);
        return mat.find();
    }

    public static boolean isMatch(Pattern pattern, String input) {
        final Matcher mat = pattern.matcher(input);
        return mat.find();
    }

    public static Map<String, String> compatParseMap(final String input) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        for (String arg : input.split("&")) {
            String[] splitArg = arg.split("=");
            if (splitArg.length > 1) {
                map.put(splitArg[0], URLDecoder.decode(splitArg[1], UTF_8));
            } else {
                map.put(splitArg[0], "");
            }
        }
        return map;
    }
}
