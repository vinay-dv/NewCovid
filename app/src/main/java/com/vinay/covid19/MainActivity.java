package com.vinay.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private List<Country_item> mcountry;
    private AssetManager assetManager;
    private SearchView searchView;
    private ImageView mliveIcon;
    private List<String> name = new ArrayList<>();
    private ArrayList<String> Countryname = new ArrayList<>();
    private ArrayList<Bitmap> bitmapList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //flags image array
        assetManager = getAssets();
        bitmapList = new ArrayList<Bitmap>();
        try{
            listAllImages();
        }catch (Exception e) {
            e.printStackTrace();
        }

        // live button
        mliveIcon = findViewById(R.id.liveicon);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        mliveIcon.startAnimation(animation);

        // Search View
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((CountryAdapter) mAdapter).getFilter().filter(newText);
                return false;
            }
        });

        Map<String, String> map = new HashMap<>();
        map = fun(1);
        printMap(map);
        createCountryList();
        buildRecyclerView();

    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    public static final List<String> country_list =
            Collections.unmodifiableList(Arrays.asList("US","Trinidad and Tobago","Lithuania","Sri Lanka","Somalia","Qatar","Japan","Dominican Republic","Lebanon","Bahrain","Israel","Mexico","Malaysia","Chad","MS Zaandam","Costa Rica","Latvia","Dominica","Kenya","Iran","Guinea","Georgia","Benin","Azerbaijan","Norway","Slovenia","Maldives","Andorra","Rwanda","Iraq","Hungary","Kyrgyzstan","Bosnia and Herzegovina","Bulgaria","Burma","Cabo Verde","Cameroon","Australia","Burkina Faso","El Salvador","Angola","Serbia","Namibia","Estonia","Belgium","Nicaragua","Mozambique","Gambia","Turkey","Tanzania","Philippines","Portugal","Afghanistan","Antigua and Barbuda","Ireland","Armenia","Diamond Princess","China","Chile","United Arab Emirates","Sweden","Mauritius","Saint Kitts and Nevis","Algeria","Albania","Egypt","Brazil","Jordan","Moldova","Germany","Cote d'Ivoire","Syria","Senegal","Canada","Czechia","Bahamas","San Marino","Guyana","Nepal","Russia","Bangladesh","Liechtenstein","US","Laos","Sudan","Taiwan*","Argentina","Belarus","Pakistan","Morocco","Kuwait","Italy","Austria","Mongolia","Montenegro","Bolivia","Gabon","Luxembourg","Peru","Haiti","Guatemala","Saudi Arabia","Suriname","United Kingdom","New Zealand","Niger","Fiji","Djibouti","Mauritania","Guinea-Bissau","Thailand","France","Barbados","Kosovo","Congo (Brazzaville)","Netherlands","Poland","Finland","Switzerland","Romania","Ukraine","Togo","Uzbekistan","Ecuador","Eswatini","Cambodia","Saint Vincent and the Grenadines","Belize","Oman","Iceland","Malta","Timor-Leste","Kazakhstan","Greece","Korea","South","Brunei","Uganda","Denmark","Singapore","Paraguay","Nigeria","Mali","Zambia","Vietnam","Jamaica","Liberia","Central African Republic","Slovakia","Indonesia","India","Saint Lucia","West Bank and Gaza","Cyprus","South Africa","Monaco","Equatorial Guinea","Cuba","Spain","Honduras","North Macedonia","Ghana","Grenada","Papua New Guinea","Zimbabwe","Venezuela","Congo (Kinshasa)","Libya","Croatia","Uruguay","Ethiopia","Tunisia","Eritrea","Panama","Seychelles","Colombia","Holy See","Madagascar","Bhutan"));
    public  Map<String, String> fun(Integer fetch){
        Map<String, String> mp = new HashMap<>();
        if(fetch==1){
            DownloadTask task= new DownloadTask();
            task.execute("");
        }
        Log.i("fun", "is called");
        for(int i=0;i<country_list.size();i++){
            String country = country_list.get(i);
            Log.i("fetch1 " + country, get_data(country));
            mp.put(country, get_data(country));
        }
        return mp;

    }


    public  class DownloadTask extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(String... strings) {
            ArrayList<String> wrong_country_names = new ArrayList<>();

            try {
                HttpResponse<String> response = Unirest.get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats")
                        .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                        .header("x-rapidapi-key", "972845f4d2msh3554974eda5a114p171a76jsna7d8826f2816")
                        .asString();

                JSONObject myObject = new JSONObject(response.getBody());
                JSONArray stats = myObject.getJSONObject("data").getJSONArray("covid19Stats");
                int confirmed_cases = 0;
                int deaths = 0;
                int recovered = 0;
                Map<String, Integer> cases_sum = new HashMap<>();
                Map<String, Integer> deaths_sum = new HashMap<>();
                Map<String, Integer> recovered_sum = new HashMap<>();
                for (int i = 0; i < stats.length(); i++) {
                    JSONObject stat_obj = stats.getJSONObject(i);
                    String country = (stat_obj.getString("country"));
                    confirmed_cases = stat_obj.getInt("confirmed");
                    deaths = stat_obj.getInt("deaths");
                    recovered = stat_obj.getInt("recovered");
                    if (!cases_sum.containsKey(country)){
                        cases_sum.put(country, 0);
                    }
                    if (!deaths_sum.containsKey(country)){
                        deaths_sum.put(country, 0);
                    }
                    if (!recovered_sum.containsKey(country)){
                        recovered_sum.put(country, 0);
                    }
                    cases_sum.put(country, (cases_sum.get(country) + confirmed_cases));
                    deaths_sum.put(country, (deaths_sum.get(country) + deaths));
                    recovered_sum.put(country, (recovered_sum.get(country) + recovered));
                }
                Map<String, String> res = new HashMap<>();
                for(int i=0; i<country_list.size(); i++){
                    String country = country_list.get(i);
                    String ret = cases_sum.get(country)+","+deaths_sum.get(country)+","+recovered_sum.get(country);
                    res.put(country, ret);
//                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(country, ret);
//                    editor.apply();
//                    Updater updater = new Updater();
//                    updater.set_data(country, ret);
                }
                Log.i("result is ", "returned");
                return res;
            } catch (UnirestException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Map<String, String> mp) {
            for(int i=0;i<country_list.size();i++){
                String country = country_list.get(i);
                String val = mp.get(country);
//                Log.i("post", val + " " + country);
                set_data(country, val);
            }
        }
    }
    public void set_data(String key, String data){
//        Log.i("set data ", "is acaaled!");
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Log.i(key, data);
        editor.putString(key, data);
        editor.apply();
    }
    public String get_data(String key){
        Log.i("fun", "get data is called");
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            Log.i(key, sharedPreferences.getString(key, ""));
            return sharedPreferences.getString(key, "");
        }
        catch (Exception exception){
            return "dd";
        }

    }

    // function to store flag images insde an array
    private void listAllImages() {
        Bitmap bitmap = null;
        try{
            String[] imgPath = assetManager.list("img");
            for(int i=0;i<imgPath.length;i++) {
                String str,path;
                String str1, nm;
                InputStream inputStream = assetManager.open("img/" + imgPath[i]);
                str = imgPath[i].replace("_", " ");
                str1 = str.replace(".png", "");
                String[] temp = str1.split(" ");
                ArrayList<String> result = new ArrayList<>();
                String tempResult = "";
                int j=0;
                for(String s: temp)
                {
                    String s1 = "";
                    s1 = s.substring(0, 1).toUpperCase();
                    String nameCapitalized = s1 + s.substring(1);

                    result.add(nameCapitalized);
                }
                for(String x: result)
                {
                    tempResult+=x+" ";
                }
                tempResult = tempResult.trim();
                    name.add(tempResult);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmapList.add(bitmap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // making a country class object (each object will represents the separate country)
    public void createCountryList() {
        mcountry = new ArrayList<>();

        for (int i = 0; i <name.size(); i++) {
           {

                String result = get_data(name.get(i));
                result = result.replaceAll(","," ");
                System.out.println("Answer is : "+result);
                String[] str = result.split(" ");
                String[] res = new String[3];
                int j=0;
                for(String s: str)
                {
                    res[j]=s;
                    j++;
                }
                mcountry.add(new Country_item(bitmapList.get(i), name.get(i),res[0],res[1],res[2]));
           }
        }
    }

    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CountryAdapter(mcountry);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
