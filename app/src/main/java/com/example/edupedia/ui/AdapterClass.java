package com.example.edupedia.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.edupedia.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ExampleViewHolder> {
    private ArrayList<SchoolItem> schoolItemList;
    private OnItemClickListener mListener;


    public AdapterClass(ArrayList <SchoolItem> schoolList){
        schoolItemList = schoolList;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        SchoolItem currentItem = schoolItemList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.schoolName.setText(currentItem.getSchoolName());
        holder.gradeCutOff.setText(currentItem.getGradeCutOff());
        holder.distance.setText(currentItem.getDistaceInfo());
    }

    @Override
    public int getItemCount() {
        return schoolItemList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView schoolName;
        public TextView gradeCutOff;
        public TextView distance;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.school_img);
            schoolName = itemView.findViewById(R.id.school_name);
            gradeCutOff = itemView.findViewById(R.id.grade_cutOff);
            distance = itemView.findViewById(R.id.distanceInfo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });

        }


    }
}
