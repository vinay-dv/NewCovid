package com.vinay.covid19;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Size;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnItemClickListener {
    private ArrayList<Country_item> mcountry;
    private AssetManager assetManager;
    private SearchView searchView;
    public Map<String, String> res;
    private ImageView mliveIcon;
    public List<String> name = new ArrayList<>();
    private ArrayList<String> Countryname = new ArrayList<>();
    public ArrayList<Bitmap> bitmapList;
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;
    private List<Integer> index = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String EXTRA_mCountryName="a";
    public static final Bitmap EXTRA_mImageResource = null;
    public static final String EXTRA_mcases="b";
    public static final String EXTRA_mdeaths="c";
    public static final String EXTRA_mrecovered="d";
    public static final String EXTRA_mnewCases="e", EXTRA_mnewDeaths="g";
    public static final String EXTRA_mactive="f", EXTRA_mcritical="h";
    public static final String EXTRA_mtime="i";
    public static final int EXTRA_POS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!ConnectionManager.isConnected(MainActivity.this))
        {
           Intent intent = new Intent(this,FirstPage.class);
           startActivity(intent);
           finish();
        }
        else {
            Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", true);
            if(isFirstRun) {
                Intent i = new Intent(this,FirstRun.class);
                startActivity(i);
                finish();
            }
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();

            fun(1);
            if(!isFirstRun) {
                Intent intent = new Intent(this, FirstEveryTime.class);
                startActivity(intent);
            }

            fun(1);
            setContentView(R.layout.activity_main);
            //making list of images
            assetManager = getAssets();
            bitmapList = new ArrayList<Bitmap>();
            listAllImages();


            // live button
            mliveIcon = findViewById(R.id.liveicon);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
            mliveIcon.startAnimation(animation);

            //Map<String, String> map = new HashMap<>();
            // map = fun(1);
            //printMap(map);
            fun(1);
            createCountryList();
            buildRecyclerView();


            // Search View
            searchView = findViewById(R.id.searchView);
            searchView.setIconifiedByDefault(false);
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
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
        }


      /*  Boolean isFirstRun = getSharedPreferences("data", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, FirstPage.class));
            Intent i = getIntent();
            finish();
            startActivity(i);
        }
        getSharedPreferences("data", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
*/
        // if (!isFirstRun) {}

    }

  /*  public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    } */

    public static final List<String> country_list
            = Collections.unmodifiableList(Arrays.asList("Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua-and-Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia-and-Herzegovina","Botswana","Brazil","British-Virgin-Islands","Brunei-","Bulgaria","Burkina-Faso","Burundi","Cabo-Verde","Cambodia","Cameroon","Canada","CAR","Caribbean-Netherlands","Cayman-Islands","Chad","Channel-Islands","Chile","China","Colombia","Congo","Costa-Rica","Croatia","Cuba","Cura&ccedil;ao","Cyprus","Czechia","Denmark","Diamond-Princess-","Djibouti","Dominica","Dominican-Republic","DRC","Ecuador","Egypt","El-Salvador","Equatorial-Guinea","Eritrea","Estonia","Eswatini","Ethiopia","Faeroe-Islands","Fiji","Finland","France","French-Guiana","French-Polynesia","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hong-Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle-of-Man","Israel","Italy","Ivory-Coast","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","MS-Zaandam","MS-Zaandam-","Myanmar","Namibia","Nepal","Netherlands","New-Caledonia","New-Zealand","Nicaragua","Niger","Nigeria","North-Macedonia","Norway","Oman","Pakistan","Palestine","Panama","Papua-New-Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto-Rico","Qatar","R&eacute;union","Romania","Russia","Rwanda","S.-Korea","Saint-Kitts-and-Nevis","Saint-Lucia","Saint-Martin","San-Marino","Saudi-Arabia","Senegal","Serbia","Seychelles","Sierra-Leone","Singapore","Sint-Maarten","Slovakia","Slovenia","Somalia","South-Africa","Spain","Sri-Lanka","St.-Barth","St.-Vincent-Grenadines","Sudan","Suriname","Sweden","Switzerland","Syria","Taiwan","Tanzania","Thailand","Timor-Leste","Togo","Trinidad-and-Tobago","Tunisia","Turkey","Turks-and-Caicos","U.S.-Virgin-Islands","UAE","Uganda","UK","Ukraine","Uruguay","USA","Uzbekistan","Vatican-City","Venezuela","Vietnam","Zambia","Zimbabwe"));

    public Map<String, String> fun(Integer fetch) {
        Map<String, String> mp = new HashMap<>();
        if (fetch == 1) {
            DownloadTask task = new DownloadTask();
            //task.execute("");
              task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        }
        // Log.i("fun", "is called");
        for (int i = 0; i < country_list.size(); i++) {
            String country = country_list.get(i);
            //  Log.i("fetch1 " + country, get_data(country));
            mp.put(country, get_data(country));
        }
        return mp;

    }


    public class DownloadTask extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(String... strings) {
            ArrayList<String> wrong_country_names = new ArrayList<>();

            try {
                HttpResponse<String> response = Unirest.get("https://covid-193.p.rapidapi.com/statistics")
                        .header("x-rapidapi-host", "covid-193.p.rapidapi.com")
                        .header("x-rapidapi-key", "a0fad8ba87mshf5576763a9d0201p1c244fjsn049592f1e521")
                        .asString();

                JSONObject myObject = new JSONObject(response.getBody());
                JSONArray stats = myObject.getJSONArray("response");
                String country;
                res = new HashMap<>();
                int totalcases, totaldeaths, totalrecovered, mactive, mcritical;
                String ret = "";
                String newCases, newDeaths;
                String timeDate = "";
                for (int i = 0; i < stats.length(); i++) {

                    //objects 3
                    JSONObject object = stats.getJSONObject(i);
                    JSONObject objectCases = object.getJSONObject("cases");
                    JSONObject objectDeaths = object.getJSONObject("deaths");

                    // variables 8
                    country = object.getString("country");
                    timeDate = object.getString("time");
                    newDeaths = objectDeaths.getString("new");
                    totalcases = objectCases.getInt("total");
                    newCases = objectCases.getString("new");
                    mcritical = objectCases.getInt("critical");
                    mactive = objectCases.getInt("active");
                    totalrecovered = objectCases.getInt("recovered");
                    totaldeaths = objectDeaths.getInt("total");

                    //saving data
                    ret = totalcases + "," + totaldeaths + "," + totalrecovered + "," + newCases + "," + mactive + "," +
                            mcritical + "," + newDeaths + "," + timeDate;
                    Log.i("time is: ",String.valueOf(get_time_from_last_fetch(timeDate)));
                    Log.i("result of " + country + ": ", ret);
                    res.put(country, ret);
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
            for (int i = 0; i < country_list.size(); i++) {
                String country = country_list.get(i);
                String val = mp.get(country);
                // Log.i("post", val + " " + country);
                set_data(country, val);
            }
        }
    }

    public void set_data(String key, String data) {
        //   Log.i("set data ", "is called!");
        SharedPreferences sharedPreferences = getSharedPreferences("apidata", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        //   Log.i("result of " + key + ": ", data);
        editor.apply();
    }

    public String get_data(String key) {
        // Log.i("fun", "get data is called");
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("apidata", MODE_PRIVATE);
            Log.i(key, sharedPreferences.getString(key, ""));
            return sharedPreferences.getString(key, "");
        } catch (Exception exception) {
            return "dd";
        }

    }

    // function to store flag images insde an array
    private void listAllImages() {
        Bitmap bitmap = null;
        try {
            String[] imgPath = assetManager.list("img");
            for (int i = 0; i < imgPath.length; i++) {
                String str, path;
                String str1, nm;
                InputStream inputStream = assetManager.open("img/" + imgPath[i]);
                str = imgPath[i].replace("_", " ");
                str1 = str.replace(".png", "");
                String[] temp = str1.split(" ");
                ArrayList<String> result = new ArrayList<>();
                String tempResult = "";

                int j = 0;
                for (String s : temp) {
                    String s1 = "";
                    s1 = s.substring(0, 1).toUpperCase();
                    String nameCapitalized = s1 + s.substring(1);

                    result.add(nameCapitalized);
                }
                for (String x : result) {
                    tempResult += x + " ";
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

    public void updateNames() {
        int id = name.indexOf("Usa");
        name.set(id, "USA");
        id = name.indexOf("Uae");
        name.set(id, "UAE");
        int total = name.size();
        for (int i = 0; i < total; i++) {
            if (name.get(i).contains("And")) {
                name.set(i, name.get(i).replace("And", "and"));
            }
            if (name.get(i).contains(" ")) {
                name.set(i, name.get(i).replace(" ", "-"));
            }
            if (name.get(i).contains("Of")) {
                name.set(i, name.get(i).replace("Of", "of"));
            }
        }
        id = name.indexOf("andorra");
        name.set(id, "Andorra");

        for (int i = 0; i < country_list.size(); i++) {
            if (name.contains(country_list.get(i))) {
                int d = name.indexOf(country_list.get(i));
                index.add(d);
            }
        }

    }

    // making a country class object (each object will represents the separate country)
    public void createCountryList() {
        updateNames();
        mcountry = new ArrayList<>();
        for (int i : index) {
            String k = name.get(i);
            String result = get_data(k);
            result = result.replaceAll(",", " ");
            result = result.replaceAll("null","0");
            result = result.replace("T", " ");
            System.out.println("Answer is : " + result);
            String[] str = result.split(" ");
            String[] res = new String[9];
            int j = 0;
            for (String s : str) {
                res[j] = s;
                j++;
            }
            if (k == "USA") {
                k = "United States of America";
            }
            if (k == "UAE") {
                {
                    k = "United Arab Emirates";
                }
            }
            k = k.replaceAll("-"," ");
            long t = get_time_from_last_fetch(res[7]+"T"+res[8])/60;


            //   Log.i("result of " + k + ": is added ", res[0]);
            mcountry.add(new Country_item(bitmapList.get(i), k, res[0], res[1], res[2],res[3],res[4],res[5],res[6],(res[7]+" Time: "+t+" minutes ago")));
        }

    }

    public static long get_time_from_last_fetch(String last_fetch_time_str){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSSZZZ");

        Date last_fetch_time = new Date();
        try{
            last_fetch_time = formatter.parse(last_fetch_time_str);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        Date current_time = new Date();
        long diff = current_time.getTime() - last_fetch_time.getTime();
        long diff_in_sec =  diff/1000;
        return (diff_in_sec);
    }
    // this part is for removing and adding for owneronly
   /* public void insertCountry(int position, String c, String d, String r) {
        mcountry.add(position, new Country_item(bitmapList.get(position), "United States", c, r, d));
        mAdapter.notifyItemInserted(position);
    }

    public void removeCountry(int position) {
        mcountry.remove(position);
    }*/

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CountryAdapter(mcountry);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(MainActivity.this);
    }
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        Country_item clickedItem = mcountry.get(position);
        // detailIntent.putExtra("item",clickedItem);

        Bitmap bitmap = clickedItem.getImageResource();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bs);

        detailIntent.putExtra("BitmapImage", bs.toByteArray());
        detailIntent.putExtra(EXTRA_mCountryName,clickedItem.getCountryName());
        detailIntent.putExtra(EXTRA_mcases,clickedItem.getCases());
        detailIntent.putExtra(EXTRA_mdeaths,clickedItem.getDeaths());
        detailIntent.putExtra(EXTRA_mrecovered,clickedItem.getRecovered());
        detailIntent.putExtra(EXTRA_mnewCases,clickedItem.getNewCases());
        detailIntent.putExtra(EXTRA_mactive,clickedItem.getActiveCases());
        detailIntent.putExtra(EXTRA_mcritical,clickedItem.getCriticalCases());
        detailIntent.putExtra(EXTRA_mnewDeaths,clickedItem.getNewDeaths());
        detailIntent.putExtra(EXTRA_mtime,clickedItem.getTime());

        startActivity(detailIntent);
    }
}
