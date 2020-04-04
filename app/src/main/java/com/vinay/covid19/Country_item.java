package com.vinay.covid19;

import android.graphics.Bitmap;

public class Country_item {

    private String mCountryName;
    private Bitmap mImageResource;
    private String mcases;
    private String mdeaths;
    private String mrecovered;
    private String mnewCases, mnewDeaths;
    private String mactive, mcritical;
    private String mtime;

    // constructor to initialize the varialbes
    public Country_item(Bitmap ImageResource, String CountryName, String cases, String deaths, String recovered,
                        String newCases, String active, String critical, String newDeaths, String time) {
        mImageResource = ImageResource;
        mCountryName = CountryName;
        mcases = cases;
        mdeaths = deaths;
        mrecovered = recovered;
        mnewCases = newCases;
        mactive = active;
        mcritical = critical;
        mnewDeaths = newDeaths;
        mtime = time;
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

    public String getNewCases()
    {
        return mnewCases;
    }

    public String getActiveCases()
    {
        return mactive;
    }

    public String getCriticalCases()
    {
        return mcritical;
    }

    public String getNewDeaths()
    {
        return mnewDeaths;
    }

    public String getTime()
    {
        return mtime;
    }

}
