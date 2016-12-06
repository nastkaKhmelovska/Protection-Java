package ua.nastka_khmelovska;

import java.text.NumberFormat;
import java.text.ParseException;

public class CodeChars {
	
	 public static final String ABC = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789 -_,.!?";
	    
	    public static String encode(String s) throws ParseException {
	        StringBuffer buf = new StringBuffer();
	        NumberFormat f = NumberFormat.getNumberInstance();
	        f.setMinimumIntegerDigits(2);
	        
	        for ( int i = 0; i < s.length(); ++i ) {
	            int pos = ABC.indexOf(s.charAt(i));
	            if ( pos < 0 )
	                throw new ParseException("Invalid character", i);
	            buf.append(f.format(pos));
	        }
	        
	        return buf.toString();
	    }
	    
	    public static String decode(String s) throws ParseException {
	        StringBuffer buf = new StringBuffer();
	        int pos;
	        
	        if ( ( s.length() & 1 ) != 0 )
	            throw new ParseException("Odd string length", s.length());
	        
	        for ( int i = 0; i < s.length(); i += 2 ) {
	            try {
	                pos = Integer.parseInt(s.substring(i, i + 2));
	            }
	            catch ( NumberFormatException nfe ) {
	                throw new ParseException("Invalid characters pair", i);
	            }
	            if ( pos < 0 || pos >= ABC.length() )
	                throw new ParseException("Invalid index", i);
	            
	            buf.append(ABC.charAt(pos));
	        }
	        
	        return buf.toString();
	    }
	    
	  /*  public static void main(String[] args) {
	        Scanner scan = new Scanner(System.in);
	        
	        while ( true ) {
	            System.out.print("String: ");
	            String s = scan.nextLine();
	            
	            if ( s.isEmpty() )
	                break;
	            
	            try {
	                String enc = encode(s);
	                System.out.println("Encoded: " + enc);
	                
	                String dec = decode(enc);
	                System.out.println("Decoded: " + dec);
	            }
	            catch ( ParseException pe) {
	                System.out.println(pe.getMessage() + " at pos " + pe.getErrorOffset());
	            }
	        }*/
	    
	 

}
