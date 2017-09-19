package csv_sample;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class CSVMap {

	public static void main(String[] args) throws IOException {
		CharStream input = CharStreams.fromFileName("demo.csv");
		CSVLexer lexer = new CSVLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CSVParser parser = new CSVParser(tokens);
		ParseTree tree = parser.file();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		Loader load = new Loader();
		walker.walk(load, tree);
		System.out.println(load.rows);
	}

}
