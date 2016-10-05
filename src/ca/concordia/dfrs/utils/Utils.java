package ca.concordia.dfrs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static boolean validDate(String input) {
		String pat = "\\d{8}" ;
        Pattern p = Pattern.compile(pat) ;
        Matcher m = p.matcher(input) ;
        return m.matches();
	}
}
