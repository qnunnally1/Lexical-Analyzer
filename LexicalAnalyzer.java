/**
* 
* @author Grpup 7 (Ryan Walden, Phillip King, Christian Enamorado, Tu Nguyen, Quantonio Nunnally)
* 
**/

/**
* The LexicalAnalyzer object has to be created as an object before use. When created, two ArrayLists are created, where one will 
* hold lexemes and the other will contain any errors encountered.
**/

import java.util.ArrayList;

public class LexicalAnalyzer {
	
	//declare list for lexemes and errors caught during analysis
	private ArrayList<String> lexemes, errors;
	
	public LexicalAnalyzer() {
		lexemes = new ArrayList<String>();
		errors = new ArrayList<String>();
		
		//add to lists their own corresponding headings to serve as beginning of string output 
		lexemes.add(String.format("Lexemes \t\t Tokens", " "));
		lexemes.add("------------------------------------------------");
		errors.add("Errors");
	}
	
	/**
	* buildWord’s job is to take the string and parse each character in search of string literals, character literals, separators, 
	* operators and keywords. It begins by removing any comments from the string passed. Next, each character in the string is 
	* iterated upon. During each iteration, two variables, word and state are maintained. State is used to hold a single character 
	* relevant to the position of the i variable in the for loop used. Word is used to hold any string literal or character. Within 
	* each iteration, if the character doesn’t match that of an operator separator, the character will be appended to word. Under 
	* the condition that an opening quote has already been encountered, each character will appended to word until another quote 
	* is found. For every match, meaning a string, character, separator, operator, or keyword was found, LexicalAnalyzer’s method, 
	* compare, is used to classify the lexeme and it’s token type.
	**/
	public void buildWord(String codes) {
		//remove comments
		codes = codes.replace("//.*", "");
		
		String word = "";
		String state = "";
		
		for(int i = 0; i< codes.length(); i++) {
			//state holds a single character during each iteration
			state = "" + codes.charAt(i);
			if(word.matches("\".*")) {
				//word has form of string literal
				if(state.matches("\"")) {
					//for a word that has the form of a string literal, append a closing quote
					word = word + "\"";
					//determine and add to lexeme list the token type for word
					compare(word);
					//set word to empty string before next iteration
					word = "";
				} else if(word.matches("")) {
					//add opening quote to empty word string to begin string literal
					word = "\"";
				} else {
					//append character to word
					word += codes.charAt(i);
				}
			} else if(state.matches("\\s+")) {
				//state character is one or more strings
				if(!word.matches(""))
				//if word is not empty, determine and add to lexeme list the token type for word
					compare(word);
				//set word to empty string before next iteration
				word = "";
			} else if(state.matches("=|>|<|%|$|!|~|\\?|:|->|==|>=|<=|!=|&&|\\++|--|\\+|-|"
					+ "\\*|/|&|^|%|<<|>>|\\+=|-=|\\*=|/=|&=|^=|%=|<<=|>>=|>>>|\\{|\\}|\\[|\\]|;|,|@|::|\\(|\\)") || 
					state.equals(".") || state.equals("...") || state.equals("|") || state.equals("||") || 
					state.equals("|=")) {
				//state character is an operator or separator
				if(!word.matches(""))
				//if word is not empty, determine and add to lexeme list the token type for word
					compare(word);
				//set word to empty string before next iteration
				word = "";
				compare(state);
			} else {
				//append character to word
				word += codes.charAt(i);
			}
		}
	}
	
	/**
	* compare is used to identify any possible lexemes and add them to their corresponding lists. This method takes the string 
	* passed in from buildWord and tests it against regular expression patterns to further identify the type of lexeme the string 
	* may belong to.  For any match, the lexeme is added to the lexeme list. Along with the lexeme being added to the list, the 
	* associated token is added to this same entry as an appended string. For any input strings that don’t match any of the regular 
	* expression patterns, they are added to the error list.
	**/
	public void compare(String word) {
		if(word.matches("\\{|\\}|\\[|\\]|;|,|@|::|\\(|\\)")) {
			lexemes.add(word + "\t\t\t separator");
		} else if(word.equals(".") || word.equals("...")) {
			lexemes.add(word + "\t\t\t separator");
		}else if(word.matches("=|>|<|%|$|!|~|\\?|:|->|==|>=|<=|!=|&&|\\++|--|\\+|-|\\*|/|&|^|%|<<|>>|\\+=|-=|\\*=|/=|"
				+ "&=|^=|%=|<<=|>>=|>>>=")) {
			lexemes.add(word + "\t\t\t operator");
		} else if(word.equals("|") || word.equals("||") || word.equals("|=")) {
			lexemes.add(word + "\t\t\t operator");
		} else if(word.matches("null|NULL")) {
			lexemes.add(word + "\t\t\t null_literal");
		} else if(word.matches("\".*\"+?")) {
			lexemes.add(word + "\t\t string_literal");
		} else if(word.matches("\'\\\\u[0-9a-fA-F]{4}\'|\'[a-zA-Z]\'")) {
			lexemes.add(word + "\t\t\t character_literal");
		} else if(word.matches("true|True|TRUE|false|False|FALSE")) {
			lexemes.add(word + "\t\t\t boolean_literal");
		} else if(word.matches("abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|"
				+ "default|do|double|else|enum|extends|final|finally|float|for|if|goto|implements|import|"
				+ "instanceof|int|interface|long|native|new|package|private|protected|public|return|"
				+ "short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|"
				+ "volatile|while")) {
			lexemes.add(word + "\t\t\t keyword");
		} else if(word.matches("[a-z_A-Z][a-zA-Z_0-9]*")) {
				lexemes.add(word + "\t\t\t identifier");
		} else if(word.matches("-?[0-9]{1,10}")) {
			lexemes.add(word + "\t\t\t integer_literal");
		} else if(word.matches("-?[0-9]{1,19}[lL]")) {
			lexemes.add(word + "\t\t\t long_literal");
		} else if(word.matches("[+-]?[0-9]*\\.[0-9]*[eE]?[+-]?[fF]?")) {
			lexemes.add(word + "\t\t\t float_literal");
		} else if(word.matches("[+-]?[0-9]*\\.[0-9]*[eE]?[+-]?[dD]?")) {
			lexemes.add(word + "\t\t\t double_literal");
		} else if(word.matches("\\b|\\t|\\n|\\f|\\r|\\\"|\\\\.|\\\\|\\[0-7]{1}|\\[0-7]{2}|\\[0-3]{1}[0-7]{2}")) {
			lexemes.add(word + "\t\t\t escape_sequence");
		} else {
			errors.add(word);
		}
	}
	
	/**
	* To view the list of lexemes and any possible error found, LexicalAnalyzer has two methods that take care of this: getLexemes 
	* and getErrors. Both methods handle the process of offering the client a view of any one of the lists’ contents. To receive 
	* all lexemes found, the client will make a call to getLexemes. This will print out a list of each lexeme found within the 
	* source code. 
	**/
	public void getLexemes(){
		for(String lexeme : lexemes) {
			System.out.println(lexeme);
		}
	}
	
	/**
	* To receive all lexemes found, the client will make a call to getLexemes. This will print out a list of each lexeme found 
	* within the source code. 
	**/
	public void getErrors(){
		for(String error : errors) {
			System.out.println(error);
		}
	}
}
