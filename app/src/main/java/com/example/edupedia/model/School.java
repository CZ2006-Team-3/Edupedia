package com.example.edupedia.model;

public class School {
    // Compulsory attributes
    private String schoolName; // school_name
    private String educationLevel; // mainlevel_code
    private String address; // address
    private String postalCode; // postal_code
    private String region; // zone_code
    private String schoolType; // type_code
    private String natureOfSchool; // nature_code
    private String sap; // sap_ind
    private String autonomous; // autonomous_ind
    private String gifted; // gifted_ind
    private String ip; // ip_ind

    // Optional Attributes
    private int drivingTime;
    private int publicTime;

    public static class SchoolBuilder {
        private String schoolName;
        private String educationLevel;
        private String address;
        private String postalCode;
        private String region;
        private String schoolType;
        private String natureOfSchool;
        private String sap;
        private String autonomous;
        private String gifted;
        private String ip;
        private int drivingTime;
        private int publicTime;

        public SchoolBuilder(String schoolName, String educationLevel, String address, String postalCode, String region, String schoolType, String natureOfSchool, String sap, String autonomous, String gifted, String ip) {
            this.schoolName = schoolName;
            this.educationLevel = educationLevel;
            this.address = address;
            this.postalCode = postalCode;
            this.region = region;
            this.schoolType = schoolType;
            this.natureOfSchool = natureOfSchool;
            this.sap = sap;
            this.autonomous = autonomous;
            this.gifted = gifted;
            this.ip = ip;
        }

        public SchoolBuilder withTime(int drivingTime, int publicTime) {
            this.drivingTime = drivingTime;
            this.publicTime = publicTime;
            return this;
        }

        public School build() {
            return new School(this);
        }
    }
}
