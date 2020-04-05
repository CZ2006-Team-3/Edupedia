package com.example.edupedia.controller;

import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.Filter;
import com.example.edupedia.model.Results;

/**
 * Factory Class to decide between retrieval Filter and Results
 */
public class DataStoreFactory {

    private DataStoreFactory() {

    }

    /**
     * method to decide Filter or Results as the DataStoreInterface
     * @param datastoreOption "Filter" or "Results"
     * @return DataStoreInterface
     */
    static DataStoreInterface getDatastore(String datastoreOption) {
        DataStoreInterface datastore = null;

        if(datastoreOption.equals("Filter")) {
            datastore = new Filter();
        } else if(datastoreOption.equals("Results")) {
            datastore = new Results();
        }

        return datastore;
    }

}

