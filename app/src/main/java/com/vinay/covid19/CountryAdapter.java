package com.vinay.covid19;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {
    //list of objetcs of country items (each object is representing the Country)
    private List<Country_item> mCountryList;
    private List<Country_item> mCountryListcopy;
    public static class CountryViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mCountryName;
        public TextView mCases;
        public TextView mDeaths;
        public TextView mRecovered;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            // finding views in country item layout file (country_item)
            mImageView = itemView.findViewById(R.id.countryflagimageView);
            mCountryName = itemView.findViewById(R.id.countryName);
            mCases = itemView.findViewById(R.id.displayCase);
            mDeaths = itemView.findViewById(R.id.displayDeaths);
            mRecovered = itemView.findViewById(R.id.displayCaseRecovered);
        }

    }

    CountryAdapter(List<Country_item> countryList)
    {
        mCountryList = countryList;
        mCountryListcopy = new ArrayList<>(mCountryList);
    }

    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);
        CountryViewHolder countryviewHolder = new CountryViewHolder(v);
        return countryviewHolder;
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        Country_item country = mCountryList.get(position);
        holder.mImageView.setImageBitmap(country.getImageResource());
        holder.mCountryName.setText(country.getCountryName());
        holder.mCases.setText(country.getCases());
        holder.mDeaths.setText(country.getDeaths());
        holder.mRecovered.setText(country.getRecovered());

    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country_item> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length()==0)
            {
                filteredList.addAll(mCountryListcopy);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Country_item item : mCountryListcopy){
                    if(item.getCountryName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCountryList.clear();
            mCountryList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

