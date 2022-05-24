package com.heaven7.java.base.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.2.7
 */
public final class StringUtils {

    public static String trimTailZero(String val){
        return new BigDecimal(val).stripTrailingZeros().toPlainString();
    }

    public static boolean rGrepl(String pattern, String mainText){
        //return Arrays.asList(pattern.split("\\|")).contains(mainText);
        return Pattern.compile(pattern).matcher(mainText).find();
    }
    public static boolean equalsAny(String str, String...keys){
        for(String key : keys){
            if(str.equals(key)){
                return true;
            }
        }
        return false;
    }
    public static String rSub(String pat, String replacement, String rawText){
        return rGsub(pat, replacement, rawText, 1);
    }
    public static String rGsub(String pat, String replacement, String rawText){
        return rGsub(pat, replacement, rawText, -1);
    }
    /**
     * grab strings (by regex-pattern) and replace it by replacement.
     * @param pat the regex pattern
     * @param replacement the replacement
     * @param rawText the row text
     * @param count the replacement count if you want. -1 for all
     * @return the replaced text. may be not changed if no matches.
     */
    public static String rGsub(String pat, String replacement, String rawText, int count){
        if(TextUtils.isEmpty(rawText)){
            return rawText;
        }
        Matcher matcher = Pattern.compile(pat).matcher(rawText);
        if(matcher.find()){
            List<String> ret = new ArrayList<>();
            int lastEnd = 0;
            int c = 0;
            do {
                int start = matcher.start();
                int end = matcher.end();
                if(start > lastEnd){
                    ret.add(rawText.substring(lastEnd, start));
                }
                //matched
                ret.add(replacement);
                lastEnd = end;
                c ++;
                if(c == count){
                    break;
                }
            }while (matcher.find());
            if(lastEnd < rawText.length()){
                ret.add(rawText.substring(lastEnd));
            }
            return CollectionUtils.joinString(ret, "");
        }else{
            return rawText;
        }
    }
    public static String extractStr1(String rawText, String reg){
        return extractStr1(rawText, reg, "");
    }
    public static String extractStr1(String rawText, String reg, String def){
        List<String> list = extractStr(rawText, reg);
        if(Predicates.isEmpty(list)){
            return def;
        }
        return list.get(0);
    }
    public static List<String> extractStr(String rawText, String reg){
        if(rawText.equals("-")){
            return Collections.emptyList();
        }
       // String regEx="[^0-9]+";
        Matcher matcher = Pattern.compile(reg).matcher(rawText);
        if(matcher.find()){
            List<String> ret = new ArrayList<>();
          //  int lastEnd = 0;
            do {
                String text = matcher.group();
              //  int start = matcher.start();
               // int end = matcher.end();
//                if(start > lastEnd){
//                    ret.add(rawText.substring(lastEnd, start));
//                }
                ret.add(text);
               // lastEnd = end;
            }while (matcher.find());
//            if(lastEnd < rawText.length() - 1){
//                ret.add(rawText.substring(lastEnd + 1));
//            }
            return ret;
        }
        return Collections.emptyList();
    }
    public static List<String> extractDigitStr(String str){
        // String regEx="[^0-9]+";
        Pattern pattern = Pattern.compile("[^0-9]+");
        String[] cs = pattern.split(str);
        if(Character.isDigit(str.charAt(0))){
            return Arrays.asList(cs);
        }
        return Arrays.asList(cs).subList(1, cs.length);
    }
    public static List<String> rStr_extract_all(String str, String reg){
        return extractStr(str, reg);
    }
}
