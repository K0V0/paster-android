package space.kovo.paster.services.httpService;

import org.json.JSONException;

public interface HttpRequestService<REQ_DTO, RES_DTO> {

    void postRequest(String url, REQ_DTO object) throws JSONException;
    void getRequest(String url) throws JSONException;
    void addHeader(String key, String content);
    void onSuccess(HttpOKResponseHandler<RES_DTO> httpOKResponseHandler);
    void onError(HttpErrorResponseHandler httpErrorResponseHandler);

}
