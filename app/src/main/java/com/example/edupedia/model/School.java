package com.example.edupedia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class School implements Serializable {
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
    private String mainCode;            // [27]
    private boolean sap;                // [29]
    private boolean autonomous;         // [30]
    private boolean gifted;             // [31]
    private boolean ip;                 // [32]
    private int gradeO;                 // [37]
    private int gradePSLE;              // [38]

    private ArrayList<String> subjectsOffered;
    private ArrayList<String> ccas;
    private ArrayList<String> moeProgramme;

    // Optional Attributes
    private double drivingTime;
    private double publicTime;
    private double distance;

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
        private String mainCode;            // [27]
        private boolean sap;                // [29]
        private boolean autonomous;         // [30]
        private boolean gifted;             // [31]
        private boolean ip;                 // [32]
        private int gradeO;                 // [37]
        private int gradePSLE;              // [38]

        private ArrayList<String> subjectsOffered;
        private ArrayList<String> ccas;
        private ArrayList<String> moeProgramme;
        private ArrayList<String> sdp;

        private int gradeCutOff;

        // Optional Attributes
        private double drivingTime;
        private double publicTime;

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

        public SchoolBuilder schoolMainCode(String mainCode) {
            this.mainCode = mainCode;
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

        public SchoolBuilder schoolCutOffO(int gradeO) {
            this.gradeO = gradeO;
            return this;
        }

        public SchoolBuilder schoolCutOffPSLE(int gradePSLE) {
            this.gradePSLE = gradePSLE;
            return this;
        }

        public School build() {
            return new School(this);
        }

    }

    private School(SchoolBuilder schoolBuilder) {
        this.schoolName = schoolBuilder.schoolName;
        this.urlAddress = schoolBuilder.urlAddress;
        this.address = schoolBuilder.address;
        this.postalCode = schoolBuilder.postalCode;
        this.telephoneNo = schoolBuilder.telephoneNo;
        this.emailAddress = schoolBuilder.emailAddress;
        this.visionStatement = schoolBuilder.visionStatement;
        this.missionStatement = schoolBuilder.missionStatement;
        this.philosophyCulture = schoolBuilder.philosophyCulture;
        this.dgpCode = schoolBuilder.dgpCode;
        this.zoneCode = schoolBuilder.zoneCode;
        this.clusterCode = schoolBuilder.clusterCode;
        this.typeCode = schoolBuilder.typeCode;
        this.natureCode = schoolBuilder.natureCode;
        this.mainCode = schoolBuilder.mainCode;
        this.sap = schoolBuilder.sap;
        this.autonomous = schoolBuilder.autonomous;
        this.gifted = schoolBuilder.gifted;
        this.ip = schoolBuilder.ip;
        this.gradeO = schoolBuilder.gradeO;
        this.gradePSLE = schoolBuilder.gradePSLE;
    }

    public void setDrivingTime(double drivingTime) {
        this.drivingTime = drivingTime;
    }

    public void setPublicTime(double publicTime) {
        this.publicTime = publicTime;
    }

    public void setDistance(double distance) {this.distance = distance; }

    public void setSubjectsOffered(ArrayList<String> subjectsOffered) {
        this.subjectsOffered = subjectsOffered;
    }

    public void setCcas(ArrayList<String> ccas) {
        this.ccas = ccas;
    }

    public void setMoeProgramme(ArrayList<String> moeProgramme) {
        this.moeProgramme = moeProgramme;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getVisionStatement() {
        return visionStatement;
    }

    public String getMissionStatement() {
        return missionStatement;
    }

    public String getPhilosophyCulture() {
        return philosophyCulture;
    }

    public String getDgpCode() {
        return dgpCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public String getClusterCode() {
        return clusterCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getNatureCode() {
        return natureCode;
    }

    public String getMainCode() {
        return mainCode;
    }

    public boolean isSap() {
        return sap;
    }

    public boolean isAutonomous() {
        return autonomous;
    }

    public boolean isGifted() {
        return gifted;
    }

    public boolean isIp() {
        return ip;
    }

    public ArrayList<String> getSubjectsOffered() {
        return subjectsOffered;
    }

    public ArrayList<String> getCcas() {
        return ccas;
    }

    public ArrayList<String> getMoeProgramme() {
        return moeProgramme;
    }

    public double getDistance() { return distance; }

    public double getDrivingTime() {
        return drivingTime;
    }

    public double getPublicTime() {
        return publicTime;
    }

    public int getGradeO() {
        return gradeO;
    }

    public int getGradePSLE() {
        return gradePSLE;
    }


    public static Comparator<School> DrivingTimeComparator = new Comparator<School>() {
        @Override
        public int compare(School school, School school2) {
            return Double.compare(school.getDrivingTime(), school2.getDrivingTime());
        }
    };

    public static Comparator<School> PublicTransportTimeComparator = new Comparator<School>() {
        @Override
        public int compare(School school, School school2) {
            return Double.compare(school.getPublicTime(), school2.getPublicTime());
        }
    };

    public static Comparator<School> DistanceComparator = new Comparator<School>() {
        @Override
        public int compare(School school, School school2) {
            return Double.compare(school.getDistance(), school2.getDistance());
        }
    };

    public static Comparator<School> NameComparator = new Comparator<School>() {
        @Override
        public int compare(School school, School t2) {
            return school.getSchoolName().compareTo(t2.getSchoolName());
        }
    };
}
