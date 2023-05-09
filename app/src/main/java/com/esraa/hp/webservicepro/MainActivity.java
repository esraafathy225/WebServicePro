package com.esraa.hp.webservicepro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
String link="https://jsonplaceholder.typicode.com/users";
URL url;
Button button,button1;
TextView textView;
InputStream inputStream;
    String result;
    HttpURLConnection urlConnection;
    StringBuffer buffer1=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.txt);
        button=findViewById(R.id.btn);
        button1=findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            url=new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            urlConnection= (HttpURLConnection) url.openConnection();
                            urlConnection.setReadTimeout(15000);
                            urlConnection.setConnectTimeout(15000);
                            urlConnection.setRequestMethod("GET");
                            inputStream=urlConnection.getInputStream();
                            int c=0;
                            StringBuffer buffer=new StringBuffer();
                            int responseCode = urlConnection.getResponseCode();
                            if (responseCode == HttpsURLConnection.HTTP_OK) {
                                while ((c = inputStream.read()) != -1) {
                                    buffer.append((char) c);
                                }
                            }
                            result=buffer.toString();
                            JSONArray array=new JSONArray(result);
                            for(int i=0;i<array.length();i++) {
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                String name = object.getString("name");
                                buffer1.append(name + " " + id+"\n");
                            }
                            inputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                          urlConnection.disconnect();
                        }
                    }
                }).start();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(buffer1);
            }
        });

    }
}
