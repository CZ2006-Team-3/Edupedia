package FilterTest;

import com.example.edupedia.model.Filter;

import org.json.JSONObject;
import org.junit.Test;

public class FilterStoreTest {
    @Test
    public void checkJSONObject() {
        Filter filter = new Filter();
        JSONObject jsonObject = filter.retrieveData();
        System.out.println(jsonObject.toString());

    }
}
