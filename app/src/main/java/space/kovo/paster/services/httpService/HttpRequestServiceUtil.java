package space.kovo.paster.services.httpService;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import space.kovo.paster.dtos.ErrorResponseDTO;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public final class HttpRequestServiceUtil {

    private HttpRequestServiceUtil() {}

    static ErrorResponseDTO convertErrors(VolleyError volleyError, Gson gson) {
        return Optional.ofNullable(volleyError)
                .map(ve -> ve.networkResponse)
                .map(ve -> ve.data)
                .map(ve -> {
                    try {
                        JSONObject error = new JSONObject(new String(ve));
                        if (error.has("message")) {
                            // form error
                            return new ErrorResponseDTO((String) error.get("code"), (String) error.get("message"));
                        } else if (error.has("messages")) {
                            // fields errors
                            JSONObject messages = (JSONObject) error.get("messages");
                            Type type = new TypeToken< HashMap<String, List<ErrorResponseDTO>> >(){}.getType();
                            return new ErrorResponseDTO((HashMap<String, List<ErrorResponseDTO>>) gson.fromJson(messages.toString(), type));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                })
                .orElse(null);
    }
}
