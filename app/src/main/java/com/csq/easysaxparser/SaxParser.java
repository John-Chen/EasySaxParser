/**
 * description : 节点解析器
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.InputStream;
import java.util.HashSet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public abstract class SaxParser {

    protected String curQName;
    protected StringBuilder curValue = new StringBuilder();

    protected SaxParser saxParser;
    protected String saxParserQName;

    /**
     * 需要生成子SaxParser的节点名称
     */
    protected HashSet<String> childParserQNames;

    public SaxParser() {

    }

    public SaxParser(HashSet<String> childParserQNames) {
        this.childParserQNames = childParserQNames;
    }

    protected void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(qName == null){
            return;
        }
        if(saxParser != null){
            saxParser.startElement(uri, qName, qName, attributes);

        }else if(childParserQNames != null && childParserQNames.contains(qName)){
            this.saxParser = dispatchTo(qName, attributes);
            if(this.saxParser != null){
                this.saxParserQName = qName;
                saxParser.parserStart(attributes);
            }

        }else{
            curQName = qName;
            if(curValue.length() > 0){
                curValue.delete(0, curValue.length());
            }
        }
    }

    protected void endElement(String uri, String localName, String qName) {
        if(qName == null){
            return;
        }
        if(qName.equals(saxParserQName)){
            if(saxParser != null){
                saxParser.parserEnd();
            }
            saxParser = null;
            saxParserQName = null;

        }else if(saxParser != null){
            saxParser.endElement(uri, qName, qName);

        }else{
            parserElementEnd(qName, curValue.toString());
            curQName = null;
            if(curValue.length() > 0){
                curValue.delete(0, curValue.length());
            }
        }
    }

    protected void characters(char[] ch, int start, int length) {
        if(saxParser != null){
            saxParser.characters(ch, start, length);

        }else{
            String data = new String(ch, start, length);
            if(data.length() > 0 && curQName != null){
                curValue.append(data);
            }
        }
    }


    /**
     * 开始解析一个输入流
     * @param is 文件输入流
     * @param rootParserQName 解析的文件根节点
     * @param rootParser 根解析器
     */
    public static void start(InputStream is,
                             final String rootParserQName,
                             final SaxParser rootParser){
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(is, new DefaultHandler(){
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if(qName == null){
                        return;
                    }
                    if(rootParser != null){
                        rootParser.startElement(uri, qName, qName, attributes);

                    }else if(qName.equals(rootParserQName)){
                        rootParser.parserStart(attributes);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if(rootParser != null){
                        rootParser.characters(ch, start, length);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(qName == null){
                        return;
                    }
                    if(qName.contains(rootParserQName)){
                        if(rootParser != null){
                            rootParser.parserEnd();
                        }

                    }else if(rootParser != null){
                        rootParser.endElement(uri, qName, qName);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 节点解析开始
     */
    public abstract void parserStart(Attributes attributes);
    /**
     * 一个子节点解析结束
     * @param value characters获得的值
     */
    public abstract void parserElementEnd(String qName, String value);
    /**
     * 解析事件需要向下传递，返回需要传递的子SaxParser
     */
    public abstract SaxParser dispatchTo(String qName, Attributes attributes);
    /**
     * 节点解析结束
     */
    public abstract void parserEnd();

}
