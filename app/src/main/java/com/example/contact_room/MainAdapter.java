package com.example.contact_room;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {

    private final RecyclerViewInterface recyclerViewInterface;
    private List<Contact> dataList;
    private Activity context;
    private RoomDB database;
    List<Contact> dataListAll;


    public MainAdapter(List<Contact> dataList, Activity context, RecyclerViewInterface recyclerViewInterface) {
        this.dataList = dataList;
        this.context = context;
        dataListAll = new ArrayList<>();
        dataListAll.addAll(dataList);
        notifyDataSetChanged();
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact data=dataList.get(position);
        database=RoomDB.getInstance(context);
        holder.name.setText(data.getName());
        //holder.job.setText(data.getJob());
        //holder.email.setText(data.getEmail());
        //holder.phone.setText(data.getPhone());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Contact> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataListAll);
            } else {
                for (Contact contact: dataListAll) {
                    if (contact.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(contact);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataList.clear();
            dataList.addAll((Collection<? extends Contact>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,phone, job, email;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            //phone=itemView.findViewById(R.id.phone);
            //job=itemView.findViewById(R.id.job);
            //email=itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos=getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Delete");
                    alert.setMessage("Are you sure you want to delete?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Contact p=dataList.get(getAdapterPosition());
                            database.mainDao().delete(p);
                            int position = getAdapterPosition();
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, dataList.size());

                            dialog.dismiss();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();return false;
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}
