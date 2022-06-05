package space.kovo.paster.services.httpRequestService;

public interface HttpErrorResponseHandler<ERR_RES_DTO> {

    void onError(ERR_RES_DTO data);
}
