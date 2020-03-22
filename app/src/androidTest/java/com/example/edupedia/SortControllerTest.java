package com.example.edupedia;

import android.content.Context;

import com.example.edupedia.model.Filter;
import com.example.edupedia.ui.MainNavigationUI;
import com.example.edupedia.ui.home.HomeFragment;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class SortControllerTest {
    @Test
    public void sortControllerTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        HomeFragment homeFragment = new HomeFragment();

//        assertNotEquals(344, homeFragment.onActivityResult(););
    }
}
