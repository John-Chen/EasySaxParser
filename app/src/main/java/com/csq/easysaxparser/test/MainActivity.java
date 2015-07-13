package com.csq.easysaxparser.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.csq.easysaxparser.R;
import com.csq.easysaxparser.SaxParser;
import com.csq.easysaxparser.test.models.Kml;

import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Integer, Kml>(){
            @Override
            protected Kml doInBackground(Void... params) {
                Kml kml = new Kml();
                try {
                    SaxParser.start(getAssets().open("test.kml"), "kml", new Kml.KmlParser(kml));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return kml;
            }

            @Override
            protected void onPostExecute(Kml kml) {
                super.onPostExecute(kml);
                TextView tvResult = (TextView) findViewById(R.id.tvResult);
                tvResult.setText(kml.toString());
            }
        }.execute();
    }


}
