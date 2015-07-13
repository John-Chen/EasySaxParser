# EasySaxParser

## 概述

dom解析占用内存大，也需要引入第三方库，所以使用相对于节省内存很多的sax解析是很好的选择。因为sax解析要麻烦很多，所以整理了一个简化android sax解析的一个工具。
实现思路：和Android Touch事件传递机制一样，把需要子解析器解析的节点往下传递。

如果有进一步简化的方法，欢迎交流！


<br>
<br>
## 使用

<br>
**abstract class SaxParser需要子类实现的方法：**
    
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

<br>
**开始解析：**

    根节点kml，根节点解析器KmlParser:
    SaxParser.start(getAssets().open("test.kml"), "kml", new Kml.KmlParser(kml));

<br>
**extends SaxParser**：
    
        public static class PlacemarkParser extends SaxParser {

        private Document document;
        private Placemark placemark;

        public PlacemarkParser(Document document) {
            super(new HashSet<String>(1));
            childParserQNames.add("Point");
            this.document = document;
        }

        @Override
        public void parserStart(Attributes attributes) {
            placemark = new Placemark();
        }

        @Override
        public void parserElementEnd(String qName, String value) {
            if(qName.equals("name")){
                placemark.name = value;

            }else if(qName.equals("when")){
                placemark.when = value;
            }
        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            if(qName.equals("Point")){
                return new Point.PointParser(placemark);
            }
            return null;
        }

        @Override
        public void parserEnd() {
            document.placemark = placemark;
        }
    }
