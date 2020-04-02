package com.example.edupedia.model;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edupedia.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Concrete class to implement DataStoreInterface. Storing and retrieval of results from background files
 */
public class Results implements DataStoreInterface {
    private static final String FILE_NAME = "results.json";

    private static final String fileDir = "/data/data/com.example.edupedia/files";

    /**
     * implements DataStoreInterface. Retrieval of list of names of selected schools from background files
     * @return ArrayList of school names
     */
    public ArrayList<String> retrieveData(){
        FileReader fis = null;
        try {
            fis = new FileReader(fileDir+"/"+FILE_NAME);
            BufferedReader br = new BufferedReader(fis);
            StringBuilder sb = new StringBuilder();
            String receiveString;

            while ((receiveString = br.readLine()) != null) {
                sb.append(receiveString);
            }

            String jsonString = sb.toString();

            ArrayList<String> results = new ArrayList<String>();
            JSONArray jArray = new JSONArray(jsonString);
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    results.add(jArray.getString(i));
                }
            }

            return results;

        } catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    /**
     * implements DataStoreInterface. Storng of list of names of selected schools to background files
     * @param results ArrayList of names of schools
     */
    //parameter takes in an ArrayList
    public void storeToMap(Object results){
        JSONArray jsonArray = new JSONArray((ArrayList<String>) results);
        String text = jsonArray.toString();
        File file = new File(fileDir+"/"+FILE_NAME);
        BufferedWriter bw = null;

        try {
            if (!file.exists()) {
                File theDir = new File(fileDir);
                if (!theDir.exists()) {
                    System.out.println("creating directory: " + theDir.getName());
                    theDir.mkdir();
                }
                file.createNewFile();

            }
            FileWriter fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            bw.write(text);
            Log.d("File Written to ", fileDir);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

