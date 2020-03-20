package com.example.edupedia.viewmodel;

import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.Filter;


public class DataStoreFactory {

    private DataStoreFactory() {

    }
    // static method to return appropriate DataStoreInterface object based on user choice
    public static DataStoreInterface getDatastore(String datastoreOption) {
        DataStoreInterface datastore = null;

        if(datastoreOption.equals("Filter")) {
            datastore = new Filter();
        } else if(datastoreOption.equals("Results")) {
            datastore = new Filter();
        } else if(datastoreOption.equals("AdvancedFilter")) {
            datastore = new Filter();
        }

        return datastore;
    }

}

