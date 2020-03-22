package com.example.edupedia.model;

import android.content.Context;
import android.content.res.Resources;

import com.example.edupedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class SchoolReader {
    public static final String CCA = "ccas";
    public static final String MOE = "moe";
    public static final String SUBJECT = "subjects";

    private static BufferedReader br = null;
    private static String line;
    private static String[] tempArray;
    private static Context context;
    private static InputStream stream;
    private static SchoolReader instance = null;

    private SchoolReader(Context context) {
        this.context = context;
    }

    public HashMap<String, School> retrieveSchools() throws IOException {
        HashMap<String, School> schools = new HashMap<String, School>();
        stream = context.getResources().openRawResource(R.raw.general_info);
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            String row = br.readLine();
            while ((row = br.readLine()) != null) {
                String[] data = row.split(",");
                School school = new School.SchoolBuilder(data[0])
                        .schoolUrlAddress(data[1])
                        .schoolAddress(data[2])
                        .schoolPostalCode(data[3])
                        .schoolTelephoneNo(data[4])
                        .schoolEmailAddress(data[8])
                        .schoolVisionStatement(data[18])
                        .schoolMissionStatement(data[19])
                        .schoolPhilosophyCulture(data[20])
                        .schoolDgpCode(data[21])
                        .schoolZoneCode(data[22])
                        .schoolClusterCode(data[23])
                        .schoolTypeCode(data[24])
                        .schoolNatureCode(data[25])
                        .schoolMainCode(data[27])
                        .sapSchool(data[28].equals("Yes"))
                        .autonomousSchool(data[29].equals("Yes"))
                        .giftedProgram(data[30].equals("Yes"))
                        .schoolIp(data[31].equals("Yes"))
                        .schoolCutOffO(Integer.parseInt(data[36]))
                        .schoolCutOffPSLE(Integer.parseInt(data[37]))
                        .build();
                schools.put(school.getSchoolName(),school);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return schools;
    }

    public void addArray(HashMap<String, School> schools, String arrayType) {
        switch(arrayType) {
            case SUBJECT:
                stream = context.getResources().openRawResource(R.raw.subjects_offered);
                break;

            case MOE:
                stream = context.getResources().openRawResource(R.raw.moe_programme);
                break;

            case CCA:
                stream = context.getResources().openRawResource(R.raw.ccas);
                break;
        }

        try {
            br = new BufferedReader(new InputStreamReader(stream));
            String nextSchoolName, schoolName;
            line = br.readLine();
            line = br.readLine();
            tempArray = line.split(",");
            nextSchoolName = tempArray[0];
            schoolName = nextSchoolName;
            ArrayList<String> array = new ArrayList<>();

            while (true) {
                while (schoolName.equals(nextSchoolName)) {
                    if(arrayType.equals(MOE) || arrayType.equals(SUBJECT)) array.add(tempArray[1]);
                    else array.add(tempArray[3]);
                    line = br.readLine();
                    if (line == null) break;
                    tempArray = line.split(",");
                    nextSchoolName = tempArray[0];
                }
                if(line==null) break;
                addArrayToSchool(schools, array, schoolName, arrayType);
                array = new ArrayList<>();
                schoolName = nextSchoolName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void addArrayToSchool(HashMap<String, School> schools, ArrayList<String> array, String schoolName, String type) {
            switch(type) {
                case CCA:
                    schools.get(schoolName).setCcas(array);
                    break;

                case MOE:
                    schools.get(schoolName).setMoeProgramme(array);
                    break;

                case SUBJECT:
                    schools.get(schoolName).setSubjectsOffered(array);
                    break;
            }
    }

    public static SchoolReader getInstance(Context context) {
        return instance==null? instance = new SchoolReader(context) : instance;
    }
}
