package space.kovo.paster.services.httpRequestService;

import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.FormErrorResponseDTO;

public class HttpRequestServiceUtil {

    static ErrorResponseDTO convertErrors(VolleyError volleyError) throws JSONException {
        JSONObject error = new JSONObject(new String(volleyError.networkResponse.data));
        if (error.has("message")) {
            // form error
            return new ErrorResponseDTO(
                    new FormErrorResponseDTO((String) error.get("code"), (String) error.get("message")));
        } else {

        }
        return null;
    }
}
