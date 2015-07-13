/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;


import com.csq.easysaxparser.SaxParser;

import org.xml.sax.Attributes;

import java.util.HashSet;

public class Document {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public String id;
    public String description;
    public String author;
    public ExtendedData extendedDatas;
    public Placemark placemark;

    // ----------------------- Constructors ----------------------


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", extendedDatas=" + extendedDatas +
                ", placemark=" + placemark +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

    public static class DocumentParser extends SaxParser {
        private Kml kml;
        private Document document;

        public DocumentParser(Kml kml) {
            super(new HashSet<String>());
            this.kml = kml;
            childParserQNames.add("ExtendedData");
            childParserQNames.add("Placemark");
        }

        @Override
        public void parserStart(Attributes attributes) {
            document = new Document();
            document.id = attributes.getValue("id");
        }

        @Override
        public void parserElementEnd(String qName, String value) {
            if(document == null){
                return;
            }
            if(qName.equals("description")){
                document.description = value;

            }else if(qName.equals("author")){
                document.author = value;
            }
        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            if(document == null){
                return null;
            }
            if(qName.equals("ExtendedData")){
                return new ExtendedData.ExtendedDataParser(document);

            }else if(qName.equals("Placemark")){
                return new Placemark.PlacemarkParser(document);
            }
            return null;
        }

        @Override
        public void parserEnd() {
            kml.document = document;
        }
    }

    // --------------------- logical fragments -----------------

}
