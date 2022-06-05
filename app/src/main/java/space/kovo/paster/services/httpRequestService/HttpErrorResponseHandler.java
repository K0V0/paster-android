package space.kovo.paster.services.httpRequestService;

public interface HttpErrorResponseHandler<RES_ERR_DTO> {

    void onError(RES_ERR_DTO data);
}
