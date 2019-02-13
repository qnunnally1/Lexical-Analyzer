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
	
	//scan letter by letter
	//word completed when encounter space, symbol, quote or operator
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
	
	//for any match, add to the lexeme or error list the word and it's token type
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
	
	//print each lexeme in list
	public void getLexemes(){
		for(String lexeme : lexemes) {
			System.out.println(lexeme);
		}
	}
	
	//print each error in list
	public void getErrors(){
		for(String error : errors) {
			System.out.println(error);
		}
	}
}
