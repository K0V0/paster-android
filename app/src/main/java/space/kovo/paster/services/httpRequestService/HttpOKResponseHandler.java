package space.kovo.paster.services.httpRequestService;

import org.json.JSONObject;

public interface HttpOKResponseHandler<RES_DTO> {

    void onData(RES_DTO data);

}
