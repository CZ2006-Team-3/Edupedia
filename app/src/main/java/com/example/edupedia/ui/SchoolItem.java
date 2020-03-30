package com.example.edupedia.ui;

import com.example.edupedia.controller.WatchlistController;

import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

/**
 * list of the items being displayed
 */
public class SchoolItem {
    private int mImageResource;
    private String schoolName;
    private String distanceInfo;
    private String gradeCutOff;
    private String publicTransport;
    private String drivingTime;
    private String region;
    private String type;
    private String ip;

//    private SharedPreferences sharedPref = this.getActivity().getPreferences(MODE_PRIVATE);
//    private String uid = sharedPref.getString(StartUI.firebase_key,"");
//
    private WatchlistController watchlistController = WatchlistController.getInstance();

    /**
     * school item constructor
     * @param ImageResource
     * @param SchoolName
     * @param GradecutOff
     * @param Distance_Calculated
     * @param region
     * @param type
     * @param ip
     */
    public SchoolItem(int ImageResource, String SchoolName, String GradecutOff, String Distance_Calculated,
                      String region, String type, String ip){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distanceInfo = Distance_Calculated;
        gradeCutOff = GradecutOff;
        this.region = region;
        this.type = type;
        this.ip = ip;

    }

    /**
     * school item constructor
     * @param ImageResource
     * @param SchoolName
     * @param GradecutOff
     * @param Distance_Calculated
     */
    public SchoolItem(int ImageResource, String SchoolName, String GradecutOff, String Distance_Calculated){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distanceInfo = Distance_Calculated;
        gradeCutOff = GradecutOff;
    }

    /**
     * gets the Region
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * gets the Type of institution
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * gets the IP of the institution
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * school item constructor
     * @param ImageResource
     * @param SchoolName
     * @param gradecutOff
     * @param Distance_Calculated
     * @param publicTiming
     * @param driving_time
     * @param region
     * @param type
     * @param ip
     */
    public SchoolItem(int ImageResource, String SchoolName, String gradecutOff, String Distance_Calculated, String publicTiming, String driving_time,
                      String region, String type, String ip){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distanceInfo = Distance_Calculated;
        gradeCutOff = gradecutOff;
        publicTransport = publicTiming;
        drivingTime = driving_time;
        this.region = region;
        this.type = type;
        this.ip = ip;
    }

    /**
     * gets the image resource
     * @return image resource
     */
    public int getmImageResource() {
        return mImageResource;
    }

    /**
     * gets the school name
     * @return school name
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * gets the grade cut off
     * @return grade cut off
     */
    public String getGradeCutOff() {
        return gradeCutOff;
    }

    /**
     * gets the distance info
     * @return distance info
     */
    public String getDistanceInfo() {
        return distanceInfo;
    }

    /**
     * gets the public transport time
     * @return public transport time
     */
    public String getPublicTransport() { return publicTransport; }

    /**
     * gets the driving time
     * @return
     */
    public String getDrivingTime() { return drivingTime; }

    public void setDistance(double distance) {
        distanceInfo = String.valueOf(distance);
    }

    //    public static Comparator<SchoolItem> DrivingTimeComparator = new Comparator<SchoolItem>() {
//        @Override
//        public int compare(SchoolItem schoolItem, SchoolItem schoolItem2) {
//            return Double.compare(schoolItem.getDrivingTime(), school2.getDrivingTime());
//        }
//    };
//
//    public static Comparator<School> PublicTransportTimeComparator = new Comparator<School>() {
//        @Override
//        public int compare(School school, School school2) {
//            return Double.compare(school.getPublicTime(), school2.getPublicTime());
//        }
//    };

    /**
     * compares between the distance of two school for sorting by distance
     */
    public static Comparator<SchoolItem> DistanceComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem school2) {
            float schoolDistance = Float.parseFloat(school.distanceInfo);
            float schoolDistance2 = Float.parseFloat(school2.distanceInfo);

            return Float.compare(schoolDistance, schoolDistance2);
        }
    };

    /**
     * compares between two school for sorting by driving time
     */
    public static Comparator<SchoolItem> DrivingTimeComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem school2) {
            return Float.compare(Float.parseFloat(school.getDrivingTime()), Float.parseFloat(school2.getDrivingTime()));
        }
    };

    /**
     * opens school info
     */
    public void openSchoolInfo(){
        //
        //
        //
        //
        //
    }

    /**
     * compares between two schools to sort in ascending or descending order of name
     */
    public static Comparator<SchoolItem> NameComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem t2) {
            return school.getSchoolName().compareTo(t2.getSchoolName());
        }
    };

    /**
     * compares between two school for sorting by the time taken to travel in public transport
     */
    public static Comparator<SchoolItem> PublicTransportTimeComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem schoolItem, SchoolItem t1) {
            return Float.compare(Float.parseFloat(schoolItem.getPublicTransport()), Float.parseFloat(t1.getPublicTransport()));
        }
    };
}
