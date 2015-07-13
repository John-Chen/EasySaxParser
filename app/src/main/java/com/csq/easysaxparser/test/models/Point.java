/**
 * description :
 * E-mail:csqwyyx@163.com
 * github:https://github.com/John-Chen
 */
package com.csq.easysaxparser.test.models;

import android.text.TextUtils;
import com.csq.easysaxparser.SaxParser;
import org.xml.sax.Attributes;

public class Point {

    // ------------------------ Constants ------------------------


    // ------------------------- Fields --------------------------

    public double latitude;
    public double longitude;
    public double altitude;

    // ----------------------- Constructors ----------------------

    public Point(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }


    // -------- Methods for/from SuperClass/Interfaces -----------

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }


    // --------------------- Methods public ----------------------


    // --------------------- Methods private ---------------------


    // --------------------- Getter & Setter -----------------


    // --------------- Inner and Anonymous Classes ---------------

    public static class PointParser extends SaxParser {

        private Placemark placemark;
        private Point point;

        public PointParser(Placemark placemark) {
            this.placemark = placemark;
        }

        @Override
        public void parserStart(Attributes attributes) {
        }

        @Override
        public void parserElementEnd(String qName, String value) {
            if(qName.equals("coordinates") && !TextUtils.isEmpty(value)){
                String[] ss = value.split(",");
                if(ss.length >= 3){
                    double lon = Double.valueOf(ss[0]);
                    double lat = Double.valueOf(ss[1]);
                    double alt = Double.valueOf(ss[2]);
                    point = new Point(lat, lon, alt);
                }
            }
        }

        @Override
        public SaxParser dispatchTo(String qName, Attributes attributes) {
            return null;
        }

        @Override
        public void parserEnd() {
            placemark.point = point;

        }
    }

    // --------------------- logical fragments -----------------

}
