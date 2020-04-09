package com.vinay.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import static com.vinay.covid19.MainActivity.EXTRA_mCountryName;
import static com.vinay.covid19.MainActivity.EXTRA_mactive;
import static com.vinay.covid19.MainActivity.EXTRA_mcases;
import static com.vinay.covid19.MainActivity.EXTRA_mcritical;
import static com.vinay.covid19.MainActivity.EXTRA_mdeaths;
import static com.vinay.covid19.MainActivity.EXTRA_mnewCases;
import static com.vinay.covid19.MainActivity.EXTRA_mnewDeaths;
import static com.vinay.covid19.MainActivity.EXTRA_mrecovered;
import static com.vinay.covid19.MainActivity.EXTRA_mtime;

public class DetailActivity extends AppCompatActivity {
    public ImageView dImageView;
    public TextView dCountryName;
    public TextView dCases;
    public TextView dDeaths;
    public TextView dRecovered;
    public TextView dNewCases;
    public TextView dActiveCases;
    public TextView dCriticalCases;
    public TextView dNewDeaths;
    public TextView dDateTime;
  //  public EditText deditText;
    public Bitmap iImageResource;
    public String iCountryName;
    public String iCases;
    public String iDeaths;
    public String iRecovered;
    public String iNewCases;
    public String iActiveCases;
    public String iCriticalCases;
    public String iNewDeaths;
    public String iDateTime;

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.left,R.anim.right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dImageView     = findViewById(R.id.dcountryflagimageView);
        dCountryName   = findViewById(R.id.dcountryName);
        dCases         = findViewById(R.id.ddisplayCase);
        dDeaths        = findViewById(R.id.ddisplayDeaths);
        dRecovered     = findViewById(R.id.ddisplayCaseRecovered);
        dNewCases      = findViewById(R.id.ddisplayNewCases);
        dActiveCases   = findViewById(R.id.ddisplayActiveCases);
        dCriticalCases = findViewById(R.id.ddisplayCriticalCases);
        dNewDeaths     = findViewById(R.id.ddsiplayNewDewaths);
        dDateTime      = findViewById(R.id.displayDateTime);
    //    deditText      = findViewById(R.id.displayiSupport);

        Intent intent = getIntent();
       // Country_item country_item = intent.getParcelableExtra("country_item");
        iImageResource = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("BitmapImage")
                ,0,getIntent().getByteArrayExtra("BitmapImage").length);
        iCountryName    = intent.getStringExtra(EXTRA_mCountryName);
        iCases          = intent.getStringExtra(EXTRA_mcases);
        iDeaths         = intent.getStringExtra(EXTRA_mdeaths);
        iRecovered      = intent.getStringExtra(EXTRA_mrecovered);
        iNewCases       = intent.getStringExtra(EXTRA_mnewCases);
        iActiveCases    = intent.getStringExtra(EXTRA_mactive);
        iCriticalCases  = intent.getStringExtra(EXTRA_mcritical);
        iNewDeaths      = intent.getStringExtra(EXTRA_mnewDeaths);
        iDateTime       = intent.getStringExtra(EXTRA_mtime);

        dImageView.setImageBitmap(iImageResource);
        dCountryName.setText(iCountryName);
        dCases.setText(iCases);
        dDeaths.setText(iDeaths);
        dRecovered.setText(iRecovered);
        dActiveCases.setText(iActiveCases);
        dCriticalCases.setText(iCriticalCases);
        dNewCases.setText(iNewCases);
        dNewDeaths.setText(iNewDeaths);
        dDateTime.setText(iDateTime);
    }

    @Override
    protected void onPause() {
        Log.d("detail","onPause");
        super.onPause();
        new Thread() {
            public void run() {
                try {
                    sleep(6*60*1000);
                    // Wipe your valuable data here
                    finishAffinity();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }.start();
    }
}

