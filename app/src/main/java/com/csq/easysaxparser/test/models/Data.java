/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;

import com.csq.easysaxparser.SaxParser;

import org.xml.sax.Attributes;

import java.util.HashSet;

public class Data {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public String name;
    public String value;

    // ----------------------- Constructors ----------------------


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

    public static class DataParser extends SaxParser {

        private ExtendedData extendedData;
        private Data data;

        public DataParser(ExtendedData extendedData) {
            super(new HashSet<String>(1));
            this.extendedData = extendedData;
        }

        public DataParser(HashSet<String> childParserQNames) {
            super(childParserQNames);
        }

        @Override
        public void parserStart(Attributes attributes) {
            data = new Data();
            data.name = attributes.getValue("name");
        }

        @Override
        public void parserElementEnd(String qName, String value) {
            if(qName.equals("value")){
                data.value = value;
            }
        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            return null;
        }

        @Override
        public void parserEnd() {
            extendedData.datas.add(data);
        }
    }

    // --------------------- logical fragments -----------------

}
