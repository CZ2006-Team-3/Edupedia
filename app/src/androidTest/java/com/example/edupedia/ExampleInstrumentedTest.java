package com.example.edupedia;

import android.content.Context;

import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolReader;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.edupedia", appContext.getPackageName());
    }
    @Test
    public void readSchools() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SchoolReader schoolReader = SchoolReader.getInstance(context);
        HashMap<String, School> schools = schoolReader.retrieveSchools();
        assertEquals(344,schools.size());
        assertEquals("NATIONAL JUNIOR COLLEGE" , schools.get("NATIONAL JUNIOR COLLEGE").getSchoolName());
        assertEquals("SPECTRA SECONDARY SCHOOL", schools.get("SPECTRA SECONDARY SCHOOL").getSchoolName());
    }

    @Test
    public void addCCA() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SchoolReader schoolReader = SchoolReader.getInstance(context);
        HashMap<String, School> schools = schoolReader.retrieveSchools();

        schoolReader.addArray(schools, SchoolReader.CCA);
        assertEquals("CULTURE AND AESTHETICS CLUB", schools.get("TAMPINES MERIDIAN JUNIOR COLLEGE").getCcas().get(0));
    }

    @Test
    public void addSubjects() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SchoolReader schoolReader = SchoolReader.getInstance(context);
        HashMap<String, School> schools = schoolReader.retrieveSchools();

        schoolReader.addArray(schools, SchoolReader.SUBJECT);
        assertEquals("CO-CURRICULAR ACTIVITIES", schools.get("NANYANG JUNIOR COLLEGE").getSubjectsOffered().get(0));
    }

    @Test
    public void addMoeProg() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SchoolReader schoolReader = SchoolReader.getInstance(context);
        HashMap<String, School> schools = schoolReader.retrieveSchools();

        schoolReader.addArray(schools, SchoolReader.MOE);
        assertEquals("LANGUAGE ELECTIVE PROGRAMME (ENGLISH)", schools.get("RAFFLES INSTITUTION").getMoeProgramme().get(0));
    }




}
