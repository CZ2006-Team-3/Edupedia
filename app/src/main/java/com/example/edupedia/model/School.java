package com.example.edupedia.model;

import java.util.ArrayList;

public class School {
    // Compulsory attributes
    private String schoolName;          // [1]
    private String urlAddress;          // [2]
    private String address;             // [3]
    private String postalCode;          // [4]
    private String telephoneNo;         // [5]
    private String emailAddress;        // [9]
    private String visionStatement;     // [19]
    private String missionStatement;    // [20]
    private String philosophyCulture;   // [21]
    private String dgpCode;             // [22]
    private String zoneCode;            // [23]
    private String clusterCode;         // [24]
    private String typeCode;            // [25]
    private String natureCode;          // [26]
    private boolean sap;                // [29]
    private boolean autonomous;         // [30]
    private boolean gifted;             // [31]
    private boolean ip;                 // [32]

    private ArrayList<String> subjectsOffered;
    private ArrayList<String> ccas;
    private ArrayList<String> moeProgramme;
    private ArrayList<String> sdp;
    // Optional Attributes
    private int drivingTime;
    private int publicTime;

    public static class SchoolBuilder {
        private String schoolName;          // [1]
        private String urlAddress;          // [2]
        private String address;             // [3]
        private String postalCode;          // [4]
        private String telephoneNo;         // [5]
        private String emailAddress;        // [9]
        private String visionStatement;     // [19]
        private String missionStatement;    // [20]
        private String philosophyCulture;   // [21]
        private String dgpCode;             // [22]
        private String zoneCode;            // [23]
        private String clusterCode;         // [24]
        private String typeCode;            // [25]
        private String natureCode;          // [26]
        private boolean sap;                // [29]
        private boolean autonomous;         // [30]
        private boolean gifted;             // [31]
        private boolean ip;                 // [32]

        private ArrayList<String> subjectsOffered;
        private ArrayList<String> ccas;
        private ArrayList<String> moeProgramme;
        private ArrayList<String> sdp;
        // Optional Attributes
        private int drivingTime;
        private int publicTime;

        public SchoolBuilder(String schoolName) {
            this.schoolName = schoolName;
        }

        public SchoolBuilder schoolUrlAddress(String urlAddress) {
            this.urlAddress = urlAddress;
            return this;
        }

        public SchoolBuilder schoolAddress(String address) {
            this.address = address;
            return this;
        }

        public SchoolBuilder schoolPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public SchoolBuilder schoolTelephoneNo(String telephoneNo) {
            this.telephoneNo = telephoneNo;
            return this;
        }

        public SchoolBuilder schoolEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public SchoolBuilder schoolVisionStatement(String visionStatement) {
            this.visionStatement = visionStatement;
            return this;
        }

        public SchoolBuilder schoolMissionStatement(String missionStatement) {
            this.missionStatement = missionStatement;
            return this;
        }

        public SchoolBuilder schoolPhilosophyCulture(String philosophyCulture) {
            this.philosophyCulture = philosophyCulture;
            return this;
        }

        public SchoolBuilder schoolDgpCode(String dgpCode) {
            this.dgpCode = dgpCode;
            return this;
        }

        public SchoolBuilder schoolZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
            return this;
        }

        public SchoolBuilder schoolClusterCode(String clusterCode) {
            this.clusterCode = clusterCode;
            return this;
        }

        public SchoolBuilder schoolTypeCode(String typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public SchoolBuilder schoolNatureCode(String natureCode) {
            this.natureCode = natureCode;
            return this;
        }

        public SchoolBuilder sapSchool(boolean sap) {
            this.sap = sap;
            return this;
        }

        public SchoolBuilder autonomousSchool(boolean autonomous) {
            this.autonomous = autonomous;
            return this;
        }

        public SchoolBuilder giftedProgram(boolean gifted) {
            this.gifted = gifted;
            return this;
        }

        public SchoolBuilder schoolIp(boolean ip) {
            this.ip = ip;
            return this;
        }

        public SchoolBuilder schoolSubjectsOffered(ArrayList<String> subjectsOffered) {
            //this.subjectsOffered = subjectsOffered.clone();
            return this;
        }

        public void setCcas(ArrayList<String> ccas) {
            this.ccas = ccas;
        }

        public void setMoeProgramme(ArrayList<String> moeProgramme) {
            this.moeProgramme = moeProgramme;
        }

        public void setSdp(ArrayList<String> sdp) {
            this.sdp = sdp;
        }

        public void setDrivingTime(int drivingTime) {
            this.drivingTime = drivingTime;
        }

        public void setPublicTime(int publicTime) {
            this.publicTime = publicTime;
        }
    }
    private School(SchoolBuilder schoolBuilder) {
        schoolBuilder.schoolName = schoolName;
        schoolBuilder.urlAddress = urlAddress;
        schoolBuilder.address = address;
        schoolBuilder.postalCode = postalCode;
        schoolBuilder.telephoneNo = telephoneNo;
        schoolBuilder.emailAddress = emailAddress;
        schoolBuilder.visionStatement = visionStatement;
        schoolBuilder.missionStatement = missionStatement;
        schoolBuilder.philosophyCulture = philosophyCulture;
        schoolBuilder.dgpCode = dgpCode;
        schoolBuilder.zoneCode = zoneCode;
        schoolBuilder.clusterCode = clusterCode;
        schoolBuilder.typeCode = typeCode;
        schoolBuilder.natureCode = natureCode;
        schoolBuilder.sap = sap;
        schoolBuilder.autonomous = autonomous;
        schoolBuilder.gifted = gifted;
        schoolBuilder.ip = ip;
        schoolBuilder.subjectsOffered = subjectsOffered;
        schoolBuilder.ccas = ccas;
        schoolBuilder.moeProgramme = moeProgramme;
        schoolBuilder.sdp = sdp;
        schoolBuilder.drivingTime = drivingTime;
        schoolBuilder.publicTime = publicTime;
    }
}
