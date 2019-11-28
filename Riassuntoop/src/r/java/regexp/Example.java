package r.java.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {

	public static void main(String[] args) {
		/*Operators*/
		// character c
		//set of characters [abc] or [a-c] (- indicates a range)
		//optionality (0 or 1) 	<exp>?
		//repetition  (0 or N)	<exp>*
		//repetition  (1 or N)	<exp>+
		//alternatives	exp1 | exp2
		//concatenation exp1exp2
		//grouping		(exp) useful to change priorities
		//Note: operators and special chars must be preceded by the quotation char \
		
		/* Examples */
		//Positive integer number [0-9]+
		//Positive integer number without leading 0 ([1-9][0-9]*)|0
		//Integer number with optional sign [+-]?(([1-9][0-9]*)|0)
		//Floating point number [+-]?(([0-9]+\.[0-9]*)|([0-9]+\.[0-9]+))
		//String "([^"\\]|\\["nrt])*"
		
		Pattern p = Pattern.compile("([+-]?)([0-9]+)");
		Matcher m = p.matcher("-4560");
		if(m.matches()) {
			for(int i=0; i<= m.groupCount(); i++) {
				System.out.println("Group "+i+" : '" + m.group(i) +"'");
			}
		}
	}

}
