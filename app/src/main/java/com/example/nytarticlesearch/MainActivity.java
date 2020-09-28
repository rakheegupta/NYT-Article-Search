package com.example.nytarticlesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//App ID 42a5f7a8-e329-4f81-904e-5313bff85f70
//api_key N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD
// sample api call
//https://api.nytimes.com/svc/search/v2/articlesearch.json?q=business&api-key=N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=business&api-key=N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD")
                        //http://publicobject.com/helloworld.txt")
                .build();
        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("faliure"+e);


                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.out.println("response faliure"+response.toString());
                    throw new IOException("Unexpected code " + response);

                } else {
                    final String responseData = response.body().string();
                    //Toast.makeText(getBaseContext(), "Connection successful", Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());
                }
            }
        });
    }

}