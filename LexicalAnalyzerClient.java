import java.io.*;

public class LexicalAnalyzerClient {

	public static void main(String[] args) {
		//declare and set string to string content of java file passed in
		String sourceCode = file2Text("Example.java");

		LexicalAnalyzer analyzer = new LexicalAnalyzer();
		
		//parse source code letter by letter to find lexemes
		analyzer.buildWord(sourceCode);
		
		//print lexemes and their corresponding tokens
		analyzer.getLexemes();
	}
	
	//return string of entire source code file
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
