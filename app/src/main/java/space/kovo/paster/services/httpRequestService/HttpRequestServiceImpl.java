package space.kovo.paster.services.httpRequestService;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestServiceImpl<REQ_DTO, RES_DTO, ERR_RES_DTO> implements HttpRequestService<REQ_DTO, RES_DTO, ERR_RES_DTO> {
    private static final String API_KEY = "Sv2t75NiOktsxI023mdVA4hvzP0rUhfF";
    private static final Gson gson = new Gson();

    private HttpOKResponseHandler<RES_DTO> httpOKResponseHandler;
    private HttpErrorResponseHandler<ERR_RES_DTO> httpErrorResponseHandler;
    private Context context;

    public HttpRequestServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void postRequest(String url, REQ_DTO object) throws JSONException {
        getData(Request.Method.POST, url, object);
    }

    @Override
    public void onSuccess(HttpOKResponseHandler<RES_DTO> httpOKResponseHandler) {
        this.httpOKResponseHandler = httpOKResponseHandler;
    }

    @Override
    public void onError(HttpErrorResponseHandler<ERR_RES_DTO> httpErrorResponseHandler) {
        this.httpErrorResponseHandler = httpErrorResponseHandler;
    }

    private void getData(int method, String url, REQ_DTO object) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                method,
                url,
                new JSONObject(gson.toJson(object)),
                response -> {
                    RES_DTO o = (RES_DTO) gson.fromJson(response.toString(), Object.class);
                    httpOKResponseHandler.onData(o);
                }, error -> {
                    System.out.println(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                    Object o = gson.fromJson(new String(error.networkResponse.data, StandardCharsets.UTF_8), Object.class);
                    httpErrorResponseHandler.onError((ERR_RES_DTO) o);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>(super.getHeaders());
                params.put("x-auth-token", API_KEY);
                return params;
            }
        };

        queue.add(jsonRequest);
    }
}
