package space.kovo.paster.libs;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class Conversions {
    private static final Gson gson = new Gson();

    public static JSONObject toJsonObject(Object pojo) throws JSONException {
        return new JSONObject(gson.toJson(pojo));
    }

}
