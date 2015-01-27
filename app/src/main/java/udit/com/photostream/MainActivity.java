package udit.com.photostream;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "e10159f989124d08b47599bfa0b1f468";
    public ArrayList<Photo> photosArrayList;
    public PhotoAdapter aphotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        //https://api.instagram.com/v1/media/popular?client_id=e10159f989124d08b47599bfa0b1f468
        photosArrayList = new ArrayList<Photo>();
        aphotos = new PhotoAdapter(this, photosArrayList);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aphotos);
        String fetchUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(fetchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson = null;
                try {
                    photosArrayList.clear();
                    photosJson = response.getJSONArray("data");
                    for(int i=0; i<photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.username = photoJson.getJSONObject("user").getString("username");
                        if(photoJson.getJSONObject("caption") != null) {
                            photo.caption = photoJson.getJSONObject("caption").getString("text");
                        }
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.height = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.count = photoJson.getJSONObject("likes").getInt("count");
                        photosArrayList.add(photo);
                    }
                    aphotos.notifyDataSetChanged();
                }
                catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
