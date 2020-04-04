package com.example.edupedia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a School object
 */

public class School implements Serializable {
    // Compulsory attributes
    /**
     * The name of the school
     */
    private String schoolName;          // [1]
    /**
     * The URL address of the school
     */
    private String urlAddress;          // [2]
    /**
     * The address of the school
     */
    private String address;             // [3]
    /**
     * The postal code of the school
     */
    private String postalCode;          // [4]
    /**
     * The telephone number of the school
     */
    private String telephoneNo;         // [5]
    /**
     * The school's email address
     */
    private String emailAddress;        // [9]
    /**
     * The school's vision Statement
     */
    private String visionStatement;     // [19]
    /**
     * The school's mission statement
     */
    private String missionStatement;    // [20]
    /**
     * The school's philosophy Culture
     */
    private String philosophyCulture;   // [21]
    /**
     * The school's dgp Code
     */
    private String dgpCode;             // [22]
    /**
     * The school's zone Code
     */
    private String zoneCode;            // [23]
    /**
     * The school's cluster code
     */
    private String clusterCode;         // [24]
    /**
     * The school'd type, i.e. Government, Independent, etc
     */
    private String typeCode;            // [25]
    /**
     * The school's nature code, i.e. Girls', Boys', Co-ed
     */
    private String natureCode;          // [26]
    /**
     * The school's main code
     */
    private String mainCode;            // [27]
    /**
     * The school's sap code
     */
    private boolean sap;                // [29]
    /**
     * Is the school autonomous
     */
    private boolean autonomous;         // [30]
    /**
     * Is the school gifted programme
     */
    private boolean gifted;             // [31]
    /**
     * Is the school IP
     */
    private boolean ip;                 // [32]
    /**
     * The estimated cut off grade for JC schools
     */
    private int gradeO;                 // [37]
    /**
     * The estimated cut off grade for Secondary Schools
     */
    private int gradePSLE;              // [38]


    /**
     * The subjects offered by the school
     */
    private ArrayList<String> subjectsOffered;
    /**
     * CCAs offered by the school
     */
    private ArrayList<String> ccas;
    /**
     * MOE programmes offered by the school
     */
    private ArrayList<String> moeProgramme;

    // Optional Attributes
    /**
     * driving time from user's location to school
     */
    private double drivingTime;
    /**
     * public transport time from user's location to school
     */
    private double publicTime;
    /**
     * distance from user's location to school
     */
    private double distance;


    /**
     * Builder class for School, with the same attributes as school
     */
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

        /**
         * Assigns to the school its name
         * @param schoolName This school's name
         */
        public SchoolBuilder(String schoolName) {
            this.schoolName = schoolName;
        }

        /**
         * Assigns to the school its URL address
         * @param urlAddress This school's URL address
         * @return the school with URL attribute
         */
        public SchoolBuilder schoolUrlAddress(String urlAddress) {
            this.urlAddress = urlAddress;
            return this;
        }

        /**
         * Assigns to the school its physical address
         * @param address This school's address
         * @return the school with the address attribute
         */
        public SchoolBuilder schoolAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Assigns to the school its postal code
         * @param postalCode This school's postal code
         * @return the school with the postal code attribute
         */
        public SchoolBuilder schoolPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Assigns to the school its telephone number
         * @param telephoneNo This school's telephone number
         * @return the school with the telephone number attribute
         */
        public SchoolBuilder schoolTelephoneNo(String telephoneNo) {
            this.telephoneNo = telephoneNo;
            return this;
        }

        /**
         * Assigns to the school its email address
         * @param emailAddress This school's email address
         * @return the school with the email address attribute
         */
        public SchoolBuilder schoolEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        /**
         * Assigns to the school its vision statement
         * @param visionStatement This school's vision statement
         * @return the school with the vision statement
         */
        public SchoolBuilder schoolVisionStatement(String visionStatement) {
            this.visionStatement = visionStatement;
            return this;
        }

        /**
         * Assigns the school its mission statement
         * @param missionStatement This school's mission statement
         * @return the school with the vision statement
         */
        public SchoolBuilder schoolMissionStatement(String missionStatement) {
            this.missionStatement = missionStatement;
            return this;
        }

        /**
         * Assigns the school its philosophy culture
         * @param philosophyCulture This school's philosophy culture
         * @return the school with its philosophy culture attribute
         */
        public SchoolBuilder schoolPhilosophyCulture(String philosophyCulture) {
            this.philosophyCulture = philosophyCulture;
            return this;
        }

        /**
         * Assign the school its DGP code
         * @param dgpCode This school's DGP code
         * @return the school's with the DGP code attribute
         */
        public SchoolBuilder schoolDgpCode(String dgpCode) {
            this.dgpCode = dgpCode;
            return this;
        }

        /**
         * Assign the school its zone code
         * @param zoneCode This school's zone code
         * @return the school with its zone code attribute
         */
        public SchoolBuilder schoolZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
            return this;
        }

        /**
         * Assign the school its cluster code
         * @param clusterCode This school's cluster code
         * @return the school with the cluster code attribute
         */
        public SchoolBuilder schoolClusterCode(String clusterCode) {
            this.clusterCode = clusterCode;
            return this;
        }


        /**
         * Assign the school its Type Code
         * @param typeCode This school's type code
         * @return the school with its type code attribute
         */
        public SchoolBuilder schoolTypeCode(String typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        /**
         * Assign the school its nature code
         * @param natureCode This school's nature code
         * @return the school with its nature code attribute
         */
        public SchoolBuilder schoolNatureCode(String natureCode) {
            this.natureCode = natureCode;
            return this;
        }

        /**
         * Assign the school its main code
         * @param mainCode This school's main code
         * @return the school with its main code attribute
         */
        public SchoolBuilder schoolMainCode(String mainCode) {
            this.mainCode = mainCode;
            return this;
        }

        /**
         * Assign the school its sap
         * @param sap This school's sap
         * @return the school with its sap attribute
         */
        public SchoolBuilder sapSchool(boolean sap) {
            this.sap = sap;
            return this;
        }

        /**
         * Assign the school its autonomy
         * @param autonomous This school's autonomy
         * @return the school its autonomy
         */
        public SchoolBuilder autonomousSchool(boolean autonomous) {
            this.autonomous = autonomous;
            return this;
        }

        /**
         * Assign the school its gifted status
         * @param gifted This school's gifted status
         * @return the school with the gifted attribute
         */
        public SchoolBuilder giftedProgram(boolean gifted) {
            this.gifted = gifted;
            return this;
        }

        /**
         * Assign the school its ip status
         * @param ip This school's ip status
         * @return the school with its ip attribute
         */
        public SchoolBuilder schoolIp(boolean ip) {
            this.ip = ip;
            return this;
        }

        /**
         * Assign the JC school its estimated cut off O-level grade
         * @param gradeO this school's O-level grade
         * @return this school with the O-Level cut off attribute
         */
        public SchoolBuilder schoolCutOffO(int gradeO) {
            this.gradeO = gradeO;
            return this;
        }

        /**
         * Assign the Secondary school its estimated A-level grade
         * @param gradePSLE this school's PSLE cut off grade
         * @return this school's PSLE cut off attribute
         */
        public SchoolBuilder schoolCutOffPSLE(int gradePSLE) {
            this.gradePSLE = gradePSLE;
            return this;
        }

        /**
         * returns the school after all the attributes have been assigned
         * @return the newly constructed school
         */
        public School build() {
            return new School(this);
        }
    }

    /**
     * Constructor for school
     * @param schoolBuilder schoolbuilder object to construct school
     */
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

    /**
     * set the driving time to the school from the user's location
     * @param drivingTime the driving time to the school
     */
    public void setDrivingTime(double drivingTime) {
        this.drivingTime = drivingTime;
    }

    /**
     * Set the public transport time to this school from user's location
     * @param publicTime this school's public transport time
     */
    public void setPublicTime(double publicTime) {
        this.publicTime = publicTime;
    }

    /**
     * Set the distance to the school from the user's input location
     * @param distance this school's distance from the user
     */
    public void setDistance(double distance) {this.distance = distance; }

    /**
     * Set the subjects offered by the school
     * @param subjectsOffered the list of subjects offered by the school
     */
    public void setSubjectsOffered(ArrayList<String> subjectsOffered) {
        this.subjectsOffered = subjectsOffered;
    }

    /**
     * Set the CCAs of this school
     * @param ccas this school's CCAs
     */
    public void setCcas(ArrayList<String> ccas) {
        this.ccas = ccas;
    }

    /**
     * Set this school's MOE programmes
     * @param moeProgramme
     */
    public void setMoeProgramme(ArrayList<String> moeProgramme) {
        this.moeProgramme = moeProgramme;
    }

    /**
     * Gets the school's name
     * @return this school's name
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * Gets the school's URL address
     * @return this school's URL address
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     * Gets this school's address
     * @return this school's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the school's postal code
     * @return this school's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the zone code of the school
     * @return this school's zone code
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * gets the type code of the school
     * @return this school's type code
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * gets the nature of the school
     * @return the school's nature code
     */
    public String getNatureCode() {
        return natureCode;
    }

    /**
     * gets the main code of the school
     * @return this school's main code
     */
    public String getMainCode() {
        return mainCode;
    }

    /**
     * gets if the school is sap
     * @return the sap logic
     */
    public boolean isSap() {
        return sap;
    }

    /**
     * gets the school's autonomy status
     * @return this school's autonomy status
     */
    public boolean isAutonomous() {
        return autonomous;
    }

    /**
     * gets if the school offers gifted programme
     * @return the school's gifted logic
     */
    public boolean isGifted() {
        return gifted;
    }

    /**
     * gets if the school is an IP school
     * @return the school's IP logic
     */
    public boolean isIp() {
        return ip;
    }

    /**
     * gets the list of subjects offered by the school
     * @return the list of subjects offerred by the school
     */
    public ArrayList<String> getSubjectsOffered() {
        return subjectsOffered;
    }

    /**
     * gets the list of CCAs offered by the school
     * @return the list of CCAs offered by the school
     */
    public ArrayList<String> getCcas() {
        return ccas;
    }

    /**
     * gets the list of MOE programmes offered by the school
     * @return the list of MOE programmes offered by the school
     */
    public ArrayList<String> getMoeProgramme() {
        return moeProgramme;
    }

    /**
     * gets the distance of the school from the user's input location
     * @return the distance to the school from the user's input location
     */
    public double getDistance() { return distance; }

    /**
     * gets the driving time to the school from the user's input location
     * @return the driving time to the school from the user's input location
     */
    public double getDrivingTime() {
        return drivingTime;
    }

    /**
     * gets the public transport time to the school from the user input location
     * @return the public transport time to the school from the user input location
     */
    public double getPublicTime() {
        return publicTime;
    }

    /**
     * gets the estimated O level cut off grade for the JC School
     * @return the estimated O level cut off grade for the JC School
     */
    public int getGradeO() {
        return gradeO;
    }

    /**
     * gets the estimated PSLE cut off grade for the Secondary school
     * @return the estimated PSLE cut off grade for the Secondary school
     */
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
