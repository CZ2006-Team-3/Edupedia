package com.example.edupedia.model;

import android.content.Context;
import android.content.res.Resources;

import com.example.edupedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SchoolReader {
    private static BufferedReader br = null;
    private static String line;
    private static String[] tempArray;
    private Context context;
    private static InputStream stream;

    public SchoolReader(Context context) {
        this.context = context;
    }

    public ArrayList<School> retrieveSchools() throws IOException {
        ArrayList<School> schools = new ArrayList<>();
        stream = context.getResources().openRawResource(R.raw.general_info);
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            String row;
            while ((row = br.readLine()) != null) {
                String[] data = row.split(",");
                School school = new School
                        .SchoolBuilder(data[0])
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
                        .sapSchool(data[28].equals("Yes"))
                        .autonomousSchool(data[29].equals("Yes"))
                        .giftedProgram(data[30].equals("Yes"))
                        .schoolIp(data[31].equals("Yes"))
                        .build();
                schools.add(school);
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
            return schools;
        }
    }
}