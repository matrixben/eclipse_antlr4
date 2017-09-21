package json_sample;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class JSONMap {

	public static void main(String[] args) throws IOException {
		CharStream input = CharStreams.fromFileName("demo2.json");
		JSONLexer lexer = new JSONLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JSONParser parser = new JSONParser(tokens);
		ParseTree tree = parser.json();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		XMLEmitter load = new XMLEmitter();
		walker.walk(load, tree);
		System.out.println(load.xml.get(tree));
		//TODO 子标签没有缩进，看看github的json.g4源码实现是怎么加\t的？
	}

}
