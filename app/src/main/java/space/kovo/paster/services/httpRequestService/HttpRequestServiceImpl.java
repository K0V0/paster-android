package space.kovo.paster.services.httpRequestService;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.FormErrorResponseDTO;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static space.kovo.paster.services.httpRequestService.HttpRequestServiceUtil.convertErrors;

public class HttpRequestServiceImpl<REQ_DTO, RES_DTO> implements HttpRequestService<REQ_DTO, RES_DTO> {
    private static final String API_KEY = "Sv2t75NiOktsxI023mdVA4hvzP0rUhfF";
    private static final Gson gson = new Gson();
    private final Class<RES_DTO> responseDTOtype;
    private final Map<String, String> headers = new HashMap<>();

    private HttpOKResponseHandler<RES_DTO> httpOKResponseHandler;
    private HttpErrorResponseHandler httpErrorResponseHandler;
    private Context context;

    public HttpRequestServiceImpl(Context context, Class<RES_DTO> responseDTOtype) {
        this.context = context;
        this.responseDTOtype = responseDTOtype;
    }

    @Override
    public void postRequest(String url, REQ_DTO object) throws JSONException {
        getData(Request.Method.POST, url, object);
    }

    @Override
    public void addHeader(String key, String content) {
        headers.put(key, content);
    }

    @Override
    public void onSuccess(HttpOKResponseHandler<RES_DTO> httpOKResponseHandler) {
        this.httpOKResponseHandler = httpOKResponseHandler;
    }

    @Override
    public void onError(HttpErrorResponseHandler httpErrorResponseHandler) {
        this.httpErrorResponseHandler = httpErrorResponseHandler;
    }

    private void getData(int method, String url, REQ_DTO object) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                method,
                url,
                new JSONObject(gson.toJson(object)),
                response -> httpOKResponseHandler.onData(gson.fromJson(
                        response.toString(),
                        responseDTOtype)),
                error -> {
                    try {
                        httpErrorResponseHandler.onError(convertErrors(error));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.putAll(super.getHeaders());
                headers.put("x-auth-token", API_KEY);
                headers.put("Accept-Language",
                        context.getResources().getConfiguration().getLocales().get(0).getLanguage());
                return headers;
            }
        };

        queue.add(jsonRequest);
    }

}
