package attic.notium.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Json {

    public static JsonArray array(JsonElement... elements) {
        JsonArray arr = new JsonArray();
        for (JsonElement e : elements) arr.add(e);
        return arr;
    }
}
