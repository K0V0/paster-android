package space.kovo.paster.services.httpService;

public interface HttpOKResponseHandler<RES_DTO> {

    void onData(RES_DTO data);

}
