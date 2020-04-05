package com.example.edupedia.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.edupedia.R;
import com.example.edupedia.controller.SearchController;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolReader;
import com.example.edupedia.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * adapter class for implementing recycler view
 */
public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ExampleViewHolder>
        implements Filterable, HomeFragment.SortEventListener {
    private ArrayList<SchoolItem> schoolItemList;
    private ArrayList<SchoolItem> schoolItemListFull;
    private OnItemClickListener mListener;
    private HashMap<String, School> schoolHashMap;
    private ArrayList<School> schoolList;

    /**
     * adapter class constructor
     * @param schoolList
     */
    public AdapterClass(ArrayList <SchoolItem> schoolList){
        schoolItemList = schoolList;
        if(schoolList!=null)
            schoolItemListFull = new ArrayList<>(schoolItemList);
        else schoolItemListFull = new ArrayList<>();
    }
    /*
    public AdapterClass(HashMap<String, School> schools) {
        schoolHashMap = schools;
    }*/
    /*public AdapterClass(ArrayList<School> schoolList) {
        this.schoolList = schoolList;
    }*/

    /**
     * implementation for HomeFragment.SortEventListener interface
     * @param sort_variable
     * @param sort_ascending
     */
    @Override
    public void onRequestSort(int sort_variable, boolean sort_ascending) {
        synchronized (schoolItemList) {
            sortBy(sort_variable, sort_ascending);
        }
    }

    /**
     * allows clicking of elements on recycler view
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onWatchListSelect(int position);
        void onCompareSelect(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    /**
     * creates recycler view of cards
     */
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    /**
     * adds required information to be displayed on the cards
     */
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        SchoolItem currentItem = schoolItemList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.schoolName.setText(currentItem.getSchoolName());
        holder.gradeCutOff.setText(currentItem.getGradeCutOff());
        holder.distance.setText(currentItem.getDistanceInfo());
    }

    @Override
    public int getItemCount() {
        return schoolItemList==null? 0 : schoolItemList.size();
    }

    public Filter getFilter() { return schoolFilter; }

    /**
     * implementation of filter class
     */
    private Filter schoolFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SchoolItem> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(schoolItemListFull);
            } else {
                for (SchoolItem item: schoolItemListFull) {
                    if (item.getSchoolName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            schoolItemList.clear();
            schoolItemList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public Filter getAdvancedFilter() { return advancedFilter; }

    /**
     * Filter object that performs filtering of ArrayAdapter based on advanced filter settings
     */
    private Filter advancedFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SchoolItem> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(schoolItemListFull);
            } else {
                filteredList.addAll(applyAdvancedFilter(schoolItemListFull, charSequence));
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            schoolItemList.clear();
            schoolItemList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    /**
     * update the distance of a given schoolitem
     * @param schoolName
     * @param distance
     */
    public void updateDistance(String schoolName, double distance) {
        //Log.d("UIStuff", "Distance Updated: ");
        for (int i = 0; i < schoolItemList.size(); i++) {
            if (schoolItemList.get(i).getSchoolName().equals(schoolName)) {
                schoolItemList.get(i).setDistance(distance);
                Log.d("UIStuff", "Distance Updated: " + distance + ", School: " + schoolName);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * Filters the ArrayList of SchoolItems based on advanced filter settings passed in as CharSequence message
     * @param SchoolItemListFull ArrayList of SchoolItems derived from results of primary search
     * @param message encode d message containing advanced filter settings
     * @return new list of SchoolItems
     */
    public List<SchoolItem> applyAdvancedFilter(ArrayList<SchoolItem> SchoolItemListFull, CharSequence message){
        ArrayList<String> regions = new ArrayList<>();
        HashMap<String, String> decodeRegion = new HashMap<>();
        decodeRegion.put("N", "NORTH");decodeRegion.put("S", "SOUTH");decodeRegion.put("E", "EAST");decodeRegion.put("W", "WEST");
        ArrayList<String> types = new ArrayList<>();
        HashMap<String, String> decodeTypes = new HashMap<>();
        decodeTypes.put("G", "GOVERNMENT SCHOOL");decodeTypes.put("A", "GOVERNMENT-AIDED SCH");
        decodeTypes.put("I", "INDEPENDENT SCHOOL");decodeTypes.put("I", "SPECIALISED SCHOOL");
        decodeTypes.put("Z", "SPECIALISED INDEPENDENT SCHOOL");
        ArrayList<String> ips = new ArrayList<>();
        HashMap<String, String> decodeIPs = new HashMap<>();
        decodeIPs.put("Y", "Yes");decodeIPs.put("N", "No");
        String[] breakUpMsg = message.toString().split("\\s+");
        for(int i=0; i<breakUpMsg.length-1; i++){
            switch(breakUpMsg[i]){
                case "REGION":
                    if(breakUpMsg[i+1]!="TYPE"|breakUpMsg[i+1]!="IP"){
                        char[] chars = breakUpMsg[i+1].toCharArray();
                        for (char ch : chars){
                            regions.add(decodeRegion.get(String.valueOf(ch)));
                        }
                    }
                    break;
                case "TYPE":
                    if(breakUpMsg[i+1]!="REGION"|breakUpMsg[i+1]!="IP"){
                        char[] chars = breakUpMsg[i+1].toCharArray();
                        for (char ch : chars){
                            types.add(decodeTypes.get(String.valueOf(ch)));
                        }
                    }
                    break;
                case "IP":
                    if(breakUpMsg[i+1]!="REGION"|breakUpMsg[i+1]!="IP"){
                        char[] chars = breakUpMsg[i+1].toCharArray();
                        for (char ch : chars){
                            ips.add(decodeIPs.get(String.valueOf(ch)));
                        }
                    }
                    break;
            }
        }
        List<SchoolItem> results = new ArrayList<SchoolItem>();
        for (SchoolItem item : SchoolItemListFull){
            if(!regions.contains(item.getRegion()))
                continue;
            if(!types.contains(item.getType()))
                continue;
            if(!ips.contains(item.getIp()))
                continue;
            results.add(item);
        }
        return results;
    }

    /**
     * viewholder that displays select attributes of the dataclass
     */
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView schoolName;
        public TextView gradeCutOff;
        public TextView distance;
        public ImageView mWatchListSelect;
        public ImageView mCompareSelect;

        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.school_img);
            schoolName = itemView.findViewById(R.id.school_name);
            gradeCutOff = itemView.findViewById(R.id.grade_cutOff);
            distance = itemView.findViewById(R.id.distanceInfo);
            mWatchListSelect = itemView.findViewById(R.id.watchListStar);
            mCompareSelect = itemView.findViewById(R.id.compareClick);

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

            mWatchListSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onWatchListSelect(position);
                            mWatchListSelect.setBackgroundResource(R.drawable.icon_awesome_star_yello);
                        }

                    }

                }
            });

            mCompareSelect.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onCompareSelect(position);
                            mCompareSelect.setBackgroundResource(R.drawable.material_compare_blue);
                        }
                    }
                }
            });
        }
    }

    /**
     * sorts based on sorting variable and sorting order
     * @param sort_variable
     * @param ascending
     */
    private void sortBy(int sort_variable, boolean ascending) {
        Comparator<SchoolItem> comp;
        switch(sort_variable) {

            case SortController.DIST:
                comp = SchoolItem.DistanceComparator;
                break;

            case SortController.DRIVING_TIME:
                comp = SchoolItem.DrivingTimeComparator;
                break;

            case SortController.TRANSPORT_TIME:
                comp = SchoolItem.PublicTransportTimeComparator;
                break;

            default:
                comp = SchoolItem.NameComparator;
                break;
        }
        Collections.sort(schoolItemList, comp);
        if(!ascending) {
            /*
            ArrayList <SchoolItem> schoolSortedList = new ArrayList<SchoolItem>();
            for (int i=schoolItemList.size()-1;i>=0;i++){
                schoolSortedList.add(schoolItemList.get(i));
            }
            Collections.copy(schoolItemList,schoolSortedList);
             */
            Collections.reverse(schoolItemList);
        }

        notifyDataSetChanged();
    }

    public ArrayList<SchoolItem> getSchoolItemList() {
        return schoolItemList;
    }
    public void setSchoolItemList(ArrayList<SchoolItem> schoolItemList) {
        this.schoolItemList = schoolItemList;
    }
}
