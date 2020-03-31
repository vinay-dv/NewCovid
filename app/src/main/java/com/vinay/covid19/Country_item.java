package com.vinay.covid19;

import android.graphics.Bitmap;

public class Country_item {
    private String mCountryName;
    private Bitmap mImageResource;
    private String mcases;
    private String mdeaths;
    private String mrecovered;

    // constructor to initialize the varialbes
    public Country_item(Bitmap ImageResource, String CountryName, String cases, String deaths, String recovered) {
        mImageResource = ImageResource;
        mCountryName = CountryName;
        mcases = cases;
        mdeaths = deaths;
        mrecovered = recovered;
    }

    // getter methods
    public Bitmap getImageResource() {
        return mImageResource;
    }

    public String getCountryName()
    {
        return mCountryName;
    }

    public String getCases()
    {
        return mcases;
    }

    public String getDeaths()
    {
        return mdeaths;
    }

    public String getRecovered()
    {
        return mrecovered;
    }
}
