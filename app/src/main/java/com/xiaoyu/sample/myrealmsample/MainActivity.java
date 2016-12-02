package com.xiaoyu.sample.myrealmsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyu.sample.library.RealmFacade;
import com.xiaoyu.sample.library.RealmOut;

public class MainActivity extends AppCompatActivity
    implements RealmOut{

    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout rootLayout;
    private RealmFacade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (LinearLayout) findViewById(R.id.container);
        rootLayout.removeAllViews();

        facade = new RealmFacade(this, this);

        facade.basicCRUD();
        facade.basicQuery();
        facade.basicLinkQuery();

        // More complex operations can be executed on another thread.
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                String info;
                info = facade.complexReadWrite();
                info += facade.complexQuery();
                return info;
            }

            @Override
            protected void onPostExecute(String result) {
                showStatus(result);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facade.close();
    }

    public void showStatus(String strng) {
        Log.i(TAG, strng);
        TextView tv = new TextView(this);
        tv.setText(strng);
        rootLayout.addView(tv);
    }


}
