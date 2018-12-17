package com.tensionup.seoul_story;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.tensionup.seoul_story.news.NewsAdapter;
import com.tensionup.seoul_story.util.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SelectItemActivity extends Activity {
    private final static String TAG = SelectItemActivity.class.getSimpleName() + "/DEV";

    private NewsAdapter adapter;
    private ListView listView;
    private String categoryTitle;
    private String curCategoryStr;

    private ImageView categoryImageView;
    private Bitmap categoryBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news);
        curCategoryStr = "main";

        categoryImageView = findViewById(R.id.newsBannerImage);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                categoryTitle= null;
            } else {
                categoryTitle= extras.getString("Category_Title");
            }
        } else {
            categoryTitle= (String) savedInstanceState.getSerializable("Category_Title");
        }

        // news area
        adapter = new NewsAdapter(this);

        TabLayout categoryTabLayout = this.findViewById(R.id.category_tabs);

        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tag = (String)tab.getTag();
                curCategoryStr = tag;
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://seoulstory.run.goorm.io/images/" + curCategoryStr + ".jpg");

                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream is = conn.getInputStream();
                            categoryBitmap = BitmapFactory.decodeStream(is);


                        } catch(IOException ex) {

                        }
                    }
                };

                mThread.start();

                try {
                    mThread.join();
                    categoryImageView.setImageBitmap(categoryBitmap);
                } catch (InterruptedException e) {

                }
                getNewsData(tag);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        int resTitleID = this.getResources().getIdentifier("category_title_"+categoryTitle, "string", this.getPackageName());
        TabLayout.Tab tab = categoryTabLayout.newTab().setText(resTitleID);
        tab.setTag(categoryTitle);
        categoryTabLayout.addTab(tab);

        listView = (ListView)this.findViewById(R.id.main_ListView);
        listView.setAdapter(adapter);
    }

    public void getNewsData(String requestCat) {
        adapter.clearData();
        adapter.notifyDataSetChanged();

        //Some url endpoint that you may have

        //http://seoulstory.run.goorm.io/category/categories?category=in:[%22welfare%22,%22env%22]&sort=post_date&order=-1&limit=10
        String myUrl = "http://seoulstory.run.goorm.io/category/categories?category="+ requestCat +"&sort=post_date&order=-1&limit=10";

        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            try {
                JSONArray jarray = new JSONArray(result);

                for(int i=0; i < jarray.length(); i++){
                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                    String category = jObject.getString("category");
                    String title = jObject.getString("title");
                    String href = jObject.getString("href");
                    String date = jObject.getString("post_date").split("T")[0];

                    int resTitleID = this.getResources().getIdentifier("category_title_en_"+category, "string", this.getPackageName());

                    adapter.addItem(getString(resTitleID), title, date, href);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
