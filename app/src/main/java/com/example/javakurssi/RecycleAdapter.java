package com.example.javakurssi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>  implements Filterable {
    private ArrayList<ItemDataModel> dataSet;
    private ArrayList<ItemDataModel> FullList;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView id;
        TextView companyForm;
        TextView registrationDate;
        LinearLayout hidden;
        MyViewHolder(View itemView) {
            super(itemView);
            //this.mAdapter = mAdapter;
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            companyForm = itemView.findViewById(R.id.companyForm);
            registrationDate = itemView.findViewById(R.id.registrationDate);
            hidden = itemView.findViewById(R.id.hidden);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            if(hidden.getVisibility() == View.GONE )
            {
                hidden.setVisibility(View.VISIBLE);
            } else {
                hidden.setVisibility(View.GONE);
            }
        }
    }

    public RecycleAdapter(ArrayList<ItemDataModel> itemList) {
        this.dataSet = itemList;
        FullList = new ArrayList<>(itemList);
    }
    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        ItemDataModel currentItem = dataSet.get(position);
        holder.name.setText(currentItem.getName());
        holder.id.setText(currentItem.getId());
        holder.companyForm.setText(currentItem.getCompanyForm());
        holder.registrationDate.setText(currentItem.getRegistrationDate());

        // sulkee ylimääräiset tiedot kun card ei ole enää näkyvissä ruudulla
        holder.hidden.setVisibility(View.GONE);

    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ItemDataModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemDataModel item : FullList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            dataSet.clear();
            dataSet.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
