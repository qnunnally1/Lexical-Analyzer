import java.util.ArrayList;

public class LexicalAnalyzer {
	
	private ArrayList<String> lexemes, errors;
	
	public LexicalAnalyzer() {
		lexemes = new ArrayList<String>();
		errors = new ArrayList<String>();
		
		lexemes.add(String.format("Lexemes \t\t Tokens", " "));
		lexemes.add("------------------------------------------------");
		errors.add("Errors");
	}
	
	public void buildWord(String codes) {
		//remove comments
		codes = codes.replace("//.*", "");
		
		String word = "";
		String state = "";
		
		//scan letter by letter
		//word completed when encounter space, symbol or operator
		for(int i = 0; i< codes.length(); i++) {
			state = "" + codes.charAt(i);
			if(word.matches("\".*")) {
				if(state.matches("\"")) {
					word = word + "\"";
					compare(word);
					word = "";
				} else if(word.matches("")) {
					word = "\"";
				} else {
					word += codes.charAt(i);
				}
			} else if(state.matches("\\s+")) {
				if(!word.matches(""))
					compare(word);
				word = "";
			} else if(state.matches("=|>|<|%|$|!|~|\\?|:|->|==|>=|<=|!=|&&|\\++|--|\\+|-|"
					+ "\\*|/|&|^|%|<<|>>|\\+=|-=|\\*=|/=|&=|^=|%=|<<=|>>=|>>>|\\{|\\}|\\[|\\]|;|,|@|::|\\(|\\)") || 
					state.equals(".") || state.equals("...") || state.equals("|") || state.equals("||") || 
					state.equals("|=")) {
				if(!word.matches(""))
					compare(word);
				word = "";
				compare(state);
			} else {
				word += codes.charAt(i);
			}
		}
	}
	
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
	
	public void getLexemes(){
		for(String lexeme : lexemes) {
			System.out.println(lexeme);
		}
	}
	
	public void getErrors(){
		for(String error : errors) {
			System.out.println(error);
		}
	}
}
