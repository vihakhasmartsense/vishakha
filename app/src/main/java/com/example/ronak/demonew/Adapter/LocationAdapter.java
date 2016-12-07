package com.example.ronak.demonew.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ronak.demonew.R;

import org.json.JSONArray;

/**
 * Created by Ronak on 12/6/2016.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements Filterable {
    private JSONArray locationList;
    private AppCompatActivity activity;
    private JSONArray tempArray;
    private LocationFilter filter;

    public LocationAdapter(AppCompatActivity activity,JSONArray jsonArray){
        this.activity = activity;
        this.locationList = jsonArray ;
        this.tempArray = jsonArray;
    }



    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.location_list,parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        holder.tvLocationCity.setText(locationList.optJSONObject(position).optString("locationName"));
        holder.llLocationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                backIntent.putExtra("isSelected", true);
                backIntent.putExtra("selectedCity", locationList.optJSONObject(position).toString());
                activity.setResult(Activity.RESULT_OK, backIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.length();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new LocationFilter(this);
        }
        return filter;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationCity;
        LinearLayout llLocationCity;
        public LocationViewHolder(View itemView) {
            super(itemView);
            tvLocationCity = (TextView) itemView.findViewById(R.id.tvLocationCity);
            llLocationCity = (LinearLayout) itemView.findViewById(R.id.llLocationLayout);
        }
    }
    public void updateLocationList(JSONArray locationList) {

        this.locationList = locationList;
        notifyDataSetChanged();
    }
    private class LocationFilter extends Filter {
        LocationAdapter locationAdapter;

        public LocationFilter(LocationAdapter locationAdapter) {
            this.locationAdapter = locationAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            JSONArray filteredArray = new JSONArray();
            for (int i = 0; i < tempArray.length(); i++) {
                if (tempArray.optJSONObject(i).optString("locationName").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    filteredArray.put(tempArray.optJSONObject(i));
                }
            }
            results.count = filteredArray.length();
            results.values = filteredArray;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            locationAdapter.locationList = (JSONArray) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
