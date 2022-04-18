package com.jorgegayer.weight_control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import java.util.LinkedList;

public class WeightListAdapter extends
            RecyclerView.Adapter<WeightListAdapter.WeightViewHolder>  {

        private SQLiteDatabase db;

        private final LinkedList<WeightData> mWeightList;
        private LayoutInflater mInflater;

        public WeightListAdapter(Context context, LinkedList<WeightData> mWeightList) {
            mInflater = LayoutInflater.from(context);
            this.mWeightList = mWeightList;
        }

        //class WeightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        class WeightViewHolder extends RecyclerView.ViewHolder {
            public TextView weightItemView;
            public TextView dateItemView;
            public TextView bmiItemView;
            final WeightListAdapter mAdapter;

            public WeightViewHolder(View itemView, WeightListAdapter adapter) {
                super(itemView);
                weightItemView = itemView.findViewById(R.id.txtHistWeight);
                dateItemView = itemView.findViewById(R.id.txtHistDate);
                bmiItemView = itemView.findViewById(R.id.txtHistBmi);
                this.mAdapter = adapter;
            }
        }

        @Override
        public WeightViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
            View mItemView = mInflater.inflate(R.layout.activity_line_item,
                    parent, false);
            return new WeightViewHolder(mItemView, this);
        }

        @Override
        public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
            String mWeight = Double.toString(mWeightList.get(position).weight);
            String mDate = mWeightList.get(position).date;
            String mBmi = mWeightList.get(position).bmi;

            holder.weightItemView.setText(mWeight);
            holder.dateItemView.setText(mDate);
            holder.bmiItemView.setText(mBmi);
        }

        @Override
        public int getItemCount() {
            return mWeightList.size();
        }
}
