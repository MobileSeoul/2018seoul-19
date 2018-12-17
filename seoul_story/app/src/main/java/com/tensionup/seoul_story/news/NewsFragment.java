package com.tensionup.seoul_story.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.tensionup.seoul_story.MainActivity;
import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.util.FileManagerUtil;
import com.tensionup.seoul_story.util.HttpGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NewsFragment extends Fragment {
    private final static String TAG = NewsFragment.class.getSimpleName() + "/DEV";

    private NewsAdapter adapter;
    private ListView listView;
    private ArrayList<String> favoriteCategories;

    private String curCategoryStr;
    private Bitmap categoryBitmap;
    private String categoryImageLinkURL;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_news, container, false );

        // news area
        adapter = new NewsAdapter(getContext());
        curCategoryStr = "main";
        favoriteCategories = FileManagerUtil.getFavoriteCategory();

        TabLayout categoryTabLayout = view.findViewById(R.id.category_tabs);
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
                    adapter.clearData();

                    String myUrl = "http://seoulstory.run.goorm.io/news/newses?category=" + curCategoryStr;

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

                                categoryImageLinkURL = jObject.getString("url");

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

                    adapter.addItem(categoryBitmap, categoryImageLinkURL);

                    getNewsData(tag);
                } catch (InterruptedException e) {

                }




            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab mainNews = categoryTabLayout.newTab().setText("관심소식");
        mainNews.setTag("main");
        categoryTabLayout.addTab(mainNews);

        for(String category :
                favoriteCategories) {
            int resTitleID = getContext().getResources().getIdentifier("category_title_"+category, "string", getContext().getPackageName());
            TabLayout.Tab tab = categoryTabLayout.newTab().setText(resTitleID);
            tab.setTag(category);
            categoryTabLayout.addTab(tab);
        }

        listView = (ListView)view.findViewById(R.id.main_ListView);
        listView.setAdapter(adapter);

        ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_news_root));

        return view;
    }

    public void getNewsData(String requestCat) {
        if( requestCat.equals("main") ) {

            requestCat = "";

            for(int i=0; i<favoriteCategories.size(); i++) {
                requestCat = requestCat.concat('"' + favoriteCategories.get(i) + '"');

                if( i != favoriteCategories.size() -1 ) {
                    requestCat = requestCat.concat(",");
                }
            }
            requestCat = "in:[" + requestCat + "]";
        }

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

                    int resTitleID = this.getResources().getIdentifier("category_title_en_"+category, "string", getContext().getPackageName());

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