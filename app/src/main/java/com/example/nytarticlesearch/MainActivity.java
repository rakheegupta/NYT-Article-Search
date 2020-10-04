package com.example.nytarticlesearch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//App ID 42a5f7a8-e329-4f81-904e-5313bff85f70
//api_key N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD
// sample api call
//https://api.nytimes.com/svc/search/v2/articlesearch.json?q=business&api-key=N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private final String mUrl ="https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private final String mApi_key="N9I7wpmmT8BPcPrnMhnohhn0KDkJjFQD";
    ArrayList<Article> mAlArticles;
    Handler mHandler;
    RecycledArticleAdapter mRecycledArticleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlArticles = new ArrayList<>();

        mRecycledArticleListAdapter = new RecycledArticleAdapter(this, mAlArticles);
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rlvArticles);
        rvItems.setAdapter(mRecycledArticleListAdapter);
        rvItems.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        searchNYT("business");

        ItemClickSupport.addTo(rvItems).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent (getBaseContext(),FullPageArticle.class);
                i.putExtra("url",mAlArticles.get(position).getmWebUrl());
                startActivity(i);
            }
        });
    }

    public void searchNYT(String query)
    {
        OkHttpClient client = new OkHttpClient();

        //q=business&fq=news_desk:("sports"%20"arts"%20"fashion")&api-key=
        //q=obama&facet_fields=source&facet=true&begin_date=20120101&end_date=20121231

        /*https://api.nytimes.com/svc/search/v2/articlesearch.json?
        q=new+york+times&page=2&sort=oldest&api-key=your-api-key

        https://api.nytimes.com/svc/search/v2/articlesearch.json?
        fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101
        &api-key=your-api-key

        https://api.nytimes.com/svc/search/v2

        sort newest, oldest, relevance
         */
        //String url = mUrl+"&api_key="+mApi_key+"&q="+query;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(mUrl).newBuilder();
        urlBuilder.addQueryParameter("api-key", mApi_key);
        urlBuilder.addQueryParameter("q", query);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
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
                                mRecycledArticleListAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView =(SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                mAlArticles.clear();
                searchNYT(query);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    public void filterResults(MenuItem item) {
        startActivityForResult (new Intent(this, FilterOptionsActivity.class),REQUEST_CODE);
    }

    protected void onActivityResult (int requestCode,
                                     int resultCode,
                                     Intent data) {
        if(resultCode==RESULT_OK && requestCode ==REQUEST_CODE){
            String name = data.getExtras().getString("begin-date");
            System.out.println("Result from filter options"+name);

//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            data.getStringExtra("begin-date");
            data.getBooleanExtra("arts",false);
            data.getBooleanExtra("fashion",false);
            data.getBooleanExtra("sports",false);
            data.getStringExtra("sort-order");


        }
        super.onActivityResult(requestCode, resultCode, data);

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