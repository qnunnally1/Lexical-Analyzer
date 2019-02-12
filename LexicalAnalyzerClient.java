import java.io.*;
import java.util.Scanner;

public class LexicalAnalyzerClient {

	public static void main(String[] args) {
		String sourceCode = file2Text("Example.java");

		LexicalAnalyzer analyzer = new LexicalAnalyzer();
		
		analyzer.buildWord(sourceCode);
		
		analyzer.getLexemes();
	}
	
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
