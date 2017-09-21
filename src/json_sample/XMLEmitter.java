package json_sample;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class XMLEmitter extends JSONBaseListener{
        //将每个子树翻译完的字符串存储在该子树的根节点中
        ParseTreeProperty<String> xml = new ParseTreeProperty<String>();
        public String getXML(ParseTree ctx){
                return xml.get(ctx);
        }
        public void setXML(ParseTree ctx, String value){
                xml.put(ctx, value);
        }
        
        public void exitAtom(JSONParser.AtomContext ctx){
                this.setXML(ctx, ctx.getText());
        }
        
        public void exitString(JSONParser.StringContext ctx){
                this.setXML(ctx, ctx.getText().replaceAll("\"", ""));
        }
        //当节点的值是一个对象或者是父节点,将其下的子节点的值匹配到自身
        public void exitObjectValue(JSONParser.ObjectValueContext ctx){
                this.setXML(ctx, this.getXML(ctx.object()));
        }
        //将json键值对转化为xml标签和文本
        public void exitPair(JSONParser.PairContext ctx){
                String tag = ctx.STRING().getText().replaceAll("\"", "");//标签
                String content = this.getXML(ctx.value());//内容
                //生成xml格式字符串
                String xmlText = String.format("<%s>%s</%s>\n", tag, content, tag);
                //将生成的xml字符串保存到pair节点
                this.setXML(ctx, xmlText);
        }
        
        public void exitAnObject(JSONParser.AnObjectContext ctx){
                StringBuffer sb = new StringBuffer();
                sb.append("\n");
                //将多个键值对字符串拼接起来
                for(JSONParser.PairContext pctx : ctx.pair()){
                        sb.append(this.getXML(pctx));
                }
                //保存拼接好的字符串到object节点
                this.setXML(ctx, sb.toString());
        }
        
        public void exitEmptyObject(JSONParser.EmptyObjectContext ctx){
                this.setXML(ctx, "");
        }
        
        public void exitArrayOfValues(JSONParser.ArrayOfValuesContext ctx){
                StringBuffer sb = new StringBuffer();
                sb.append("\n");
                //将json的数组内容用<element>包装
                for(JSONParser.ValueContext vctx : ctx.value()){
                        sb.append("<element>");
                        sb.append(this.getXML(vctx));
                        sb.append("</element>\n");
                }
                this.setXML(ctx, sb.toString());
        }
        
        public void exitEmptyArray(JSONParser.EmptyArrayContext ctx){
                this.setXML(ctx, "");
        }
        
        public void exitArrayValue(JSONParser.ArrayValueContext ctx){
                this.setXML(ctx, this.getXML(ctx.array()));
        }
        
        public void exitJson(JSONParser.JsonContext ctx){
        	this.setXML(ctx, this.getXML(ctx.getChild(0)));
        }
}

