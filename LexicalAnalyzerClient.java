/**
* 
* @author Grpup 7 (Ryan Walden, Phillip King, Christian Enamorado, Tu Nguyen, Quantonio Nunnally)
* 
* LexicalAnalyzer is an object that will take source code as an input and keep a list of lexemes found within the source code. Upon 
* retrieval of the lexemes kept within the list, a user will be returned any lexemes found and its associated toke identifier.
**/

import java.io.*;

public class LexicalAnalyzerClient {

	/**
	* The LexicalAnalyzer object has to be created as an object before use.
	**/
	public static void main(String[] args) {
		//declare and set string to string content of java file passed in
		String sourceCode = file2Text("Example.java");

		LexicalAnalyzer analyzer = new LexicalAnalyzer();
		
		//parse source code letter by letter to find lexemes
		analyzer.buildWord(sourceCode);
		
		//print lexemes and their corresponding tokens
		analyzer.getLexemes();
	}
	
	/**
	* Before any lexemes are found, the source file is needed. To handle this, the client locates the source code file by passing 
	* its directory as input to a method that returns a string of the complete text within the source code file by use of a 
	* StringBuilder and BufferedReader. The BufferedReader will read each line of the source code’s file and append this line, as 
	* a string, to the string to be returned. After this string is returned, the client will use this string as an input to the 
	* LexicalAnalyzer’s method, buildWord. 
	**/
	private static String file2Text(String path) {
		StringBuilder builder = new StringBuilder();
		
		String fileString = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			while((fileString = br.readLine()) != null) {
				builder.append(fileString + "\n");
			}
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
	}

}
