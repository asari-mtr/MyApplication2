package com.example.asari_mtr.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void changeLabel(View view){
//        Log.v("TEST", "Clicked");
        final TextView tv = (TextView) findViewById(R.id.myLabel);

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... v) {
                String message = null;
                try {
                    URI uri = new URI("http://192.168.11.5:8124");

                    HttpGet httpGet = new HttpGet();
                    httpGet.setURI(uri);
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
                    HttpEntity entity = httpResponse.getEntity();

                    JSONObject object = new JSONObject(EntityUtils.toString(entity));
                    message = object.getString("message");

                    Log.v("Test", message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return message;
            }

            @Override
            protected void onPostExecute(String s) {
                tv.setText(s);
            }
        };
        asyncTask.execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
