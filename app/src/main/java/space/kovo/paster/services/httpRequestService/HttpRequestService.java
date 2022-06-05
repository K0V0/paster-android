package space.kovo.paster.services.httpRequestService;

import org.json.JSONException;

public interface HttpRequestService<REQ_DTO, RES_DTO, ERR_RES_DTO> {

    void postRequest(String url, REQ_DTO object) throws JSONException;
    void onSuccess(HttpOKResponseHandler<RES_DTO> httpOKResponseHandler);
    void onError(HttpErrorResponseHandler<ERR_RES_DTO> httpErrorResponseHandler);

}
