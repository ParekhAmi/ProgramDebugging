package com.example.programdebugging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    TextView out1,out2,out3,out4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        out1 = findViewById(R.id.out1);
        out2 = findViewById(R.id.out2);
        out3 = findViewById(R.id.out3);
        out4 = findViewById(R.id.out4);

        new MyTask().execute();
    }
    private class MyTask extends AsyncTask<Void, Void, Void>{
        String o1,o2,o3,o4;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url =null;

            Intent myNewIntent = getIntent();

            int InfoReceivedId = myNewIntent.getIntExtra("ID",99);

            try {
                url = new URL("http://172.26.30.16:8080/employee_App/cegep/mobile/singleEmployee&"+InfoReceivedId);

                HttpURLConnection client = null;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL:" + url);
                System.out.println("Response Code : "+ responseCode);

                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());

                JSONObject obj =new JSONObject(response.toString());
                o1 = ""+obj.getInt("id");
                o2 = ""+obj.getInt("salary");
                o3 = obj.getString("fname");
                o4 = obj.getString("lname");

            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            out1.setText(o1);
            out2.setText(o2);
            out3.setText(o3);
            out4.setText(o4);
            super.onPostExecute(result);
        }
    }



}
