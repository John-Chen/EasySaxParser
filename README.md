# EasySaxParser

## 概述

dom解析占用内存大，也需要引入第三方库，所以使用相对于节省内存很多的sax解析是很好的选择。因为sax解析要麻烦很多，所以整理了一个简化android sax解析的一个工具。

如果有进一步简化的方法，欢迎交流！


<br>
<br>
## 使用

<br>
**开始解析：**
    
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
