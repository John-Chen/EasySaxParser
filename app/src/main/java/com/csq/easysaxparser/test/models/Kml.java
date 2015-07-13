/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;

import com.csq.easysaxparser.SaxParser;

import org.xml.sax.Attributes;

import java.util.HashSet;

public class Kml {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public Document document;

    // ----------------------- Constructors ----------------------


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "Kml{" +
                "document=" + document +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

    public static class KmlParser extends SaxParser {

        private Kml kml;

        public KmlParser(Kml kml) {
            super(new HashSet<String>());
            this.kml = kml;
            childParserQNames.add("Document");
        }

        @Override
        public void parserStart(Attributes attributes) {

        }

        @Override
        public void parserElementEnd(String qName, String value) {

        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            if(qName.equals("Document")){
                return new Document.DocumentParser(kml);
            }
            return null;
        }

        @Override
        public void parserEnd() {

        }

    }

    // --------------------- logical fragments -----------------

}
