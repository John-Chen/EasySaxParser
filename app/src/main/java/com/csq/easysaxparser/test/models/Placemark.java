/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;


import com.csq.easysaxparser.SaxParser;

import org.xml.sax.Attributes;

import java.util.HashSet;

public class Placemark {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public String name;
    public String when;
    public Point point;

    // ----------------------- Constructors ----------------------


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "Placemark{" +
                "name='" + name + '\'' +
                ", when='" + when + '\'' +
                ", point=" + point +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

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

    // --------------------- logical fragments -----------------

}
