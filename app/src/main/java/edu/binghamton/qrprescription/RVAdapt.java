package edu.binghamton.qrprescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapt extends RecyclerView.Adapter<RVAdapt.DataHolder> {

    ArrayList<MedEntry> list;
    static ArrayList<MedEntry> list2;

    RVAdapt(ArrayList<MedEntry> list ){
        this.list = list;
        list2 = new ArrayList<>();
        list2.addAll(list);
    }

    public static class DataHolder extends RecyclerView.ViewHolder {

        TextView name, dosage;

        DataHolder(final View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tvName);
            dosage = (TextView) v.findViewById(R.id.tvDosage);
        }

    }


//    public void filter(String text) {
//        list.clear();
//        if(text.isEmpty()){
//            list.addAll(list2);
//        } else{
//            text = text.toLowerCase();
//            for(WordData wd: list2){
//                if(wd.getWord().toString().toLowerCase().contains(text)){
//                    list.add(wd);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        DataHolder dh = new DataHolder(v);
        return dh;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        String dosage = "";
        if(list.get(position).getMorning() != 0) dosage += "\nMorning - " + list.get(position).getMorning();
        if(list.get(position).getAfternoon() != 0) dosage += "\nAfternoon - " + list.get(position).getAfternoon();
        if(list.get(position).getNight() != 0) dosage += "\nNight - " + list.get(position).getNight();
        holder.dosage.setText(dosage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}