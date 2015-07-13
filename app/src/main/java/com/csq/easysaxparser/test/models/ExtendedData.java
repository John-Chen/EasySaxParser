/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;

import com.csq.easysaxparser.SaxParser;
import org.xml.sax.Attributes;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ExtendedData {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public List<Data> datas = new LinkedList<>();

    // ----------------------- Constructors ----------------------


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "ExtendedData{" +
                "datas=" + datas +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

    public static class ExtendedDataParser extends SaxParser {

        private Document document;
        private ExtendedData extendedData;

        public ExtendedDataParser(Document document) {
            super(new HashSet<String>(1));
            childParserQNames.add("Data");
            this.document = document;
        }

        @Override
        public void parserStart(Attributes attributes) {
            extendedData = new ExtendedData();
        }

        @Override
        public void parserElementEnd(String qName, String value) {

        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            if(qName.equals("Data")){
                return new Data.DataParser(extendedData);
            }
            return null;
        }

        @Override
        public void parserEnd() {
            document.extendedDatas = extendedData;
        }
    }

    // --------------------- logical fragments -----------------

}
