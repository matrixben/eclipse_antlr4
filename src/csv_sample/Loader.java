package csv_sample;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Loader extends CSVBaseListener{
	public final String EMPTY = "";
	
	List<Map<String, String>> rows = new ArrayList<Map<String,String>>();
	List<String> header;
	List<String> currentRowFieldValues;
	
	@Override
	public void exitText(CSVParser.TextContext ctx){
		this.currentRowFieldValues.add(ctx.TEXT().getText());
	}
	
	@Override
	public void exitString(CSVParser.StringContext ctx){
		this.currentRowFieldValues.add(ctx.STRING().getText());
	}
	
	@Override
	public void exitEmpty(CSVParser.EmptyContext ctx){
		this.currentRowFieldValues.add(this.EMPTY);
	}
	
	@Override
	public void exitHeader(CSVParser.HeaderContext ctx){
		this.header = new ArrayList<String>();
		this.header.addAll(this.currentRowFieldValues);//添加整行
	}
	
	@Override
	public void enterRow(CSVParser.RowContext ctx){
		this.currentRowFieldValues = new ArrayList<String>();
	}
	@Override
	public void exitRow(CSVParser.RowContext ctx){
		//如果当前是标题行，则什么都不做
		if (ctx.getParent().getRuleIndex() == CSVParser.RULE_header) {
			return;
		}
		//如果是数据行，则保存标题-数据的键值对
		Map<String, String> map = new LinkedHashMap<String, String>();
		int i = 0;
		for (String value : currentRowFieldValues) {
			map.put(this.header.get(i), value);
			i++;
		}
		this.rows.add(map);
	}
}
