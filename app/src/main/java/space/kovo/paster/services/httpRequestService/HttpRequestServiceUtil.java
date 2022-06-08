package space.kovo.paster.services.httpRequestService;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.FormErrorResponseDTO;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class HttpRequestServiceUtil {

    static ErrorResponseDTO convertErrors(VolleyError volleyError, Gson gson) throws JSONException {
        JSONObject error = new JSONObject(new String(volleyError.networkResponse.data));
        if (error.has("message")) {
            // form error
            return new ErrorResponseDTO(
                    new FormErrorResponseDTO((String) error.get("code"), (String) error.get("message")));
        } else if (error.has("messages")) {
            // fields errors
            JSONObject messages = (JSONObject) error.get("messages");
            Type type = new TypeToken< HashMap<String, List<ErrorResponseDTO>> >(){}.getType();
            return new ErrorResponseDTO((HashMap<String, List<ErrorResponseDTO>>) gson.fromJson(messages.toString(), type));
        }
        return null;
    }
}
