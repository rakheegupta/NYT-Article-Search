package com.example.nytarticlesearch;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.nytarticlesearch.adapters.RecycledArticleAdapter;
import com.example.nytarticlesearch.models.Article;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    ArrayList<Article> mAlArticles;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlArticles = new ArrayList<>();

        final RecycledArticleAdapter recycledArticleListAdapter = new RecycledArticleAdapter(this, mAlArticles);
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rlvArticles);
        rvItems.setAdapter(recycledArticleListAdapter);
        rvItems.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=business&api-key=N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD")
                //http://publicobject.com/helloworld.txt")
                .build();

        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("faliure" + e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.out.println("response faliure" + response.toString());
                    throw new IOException("Unexpected code " + response);
                } else {
                    // cannot use network on main ui thread NetworkOnMainThreadException
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println(responseData);
                                JSONObject jsonResponseData = new JSONObject(responseData);
                                mAlArticles.addAll(Article.extractFromJsonResponse(jsonResponseData.getJSONObject("response").getJSONArray("docs")));
                                System.out.println(mAlArticles.size());
                                System.out.println(mAlArticles.toString());
                                recycledArticleListAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

}

/*handler
// Read data on the worker thread
                    HandlerThread handlerThread = new HandlerThread("ProcessJsonResponse");
                    handlerThread.start();

                    mHandler=new Handler(handlerThread.getLooper()){
                        @Override
                        public void handleMessage(Message msg) {
                            // Process received messages here!
                        }
                    };
                    mHandler.post
                     mHandler.sendEmptyMessage(0);
 */