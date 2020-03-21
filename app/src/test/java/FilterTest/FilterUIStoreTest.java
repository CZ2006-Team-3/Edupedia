package FilterTest;

import com.example.edupedia.model.Filter;

import org.json.JSONObject;
import org.junit.Test;

import java.net.URL;

public class FilterUIStoreTest {
    @Test
    public void checkJSONObject() {
        Filter filter = new Filter();
        JSONObject jsonObject = filter.retrieveData();
        System.out.println(jsonObject.toString());
        this.getClass().getClassLoader().getResource("test.json");
    }
}
