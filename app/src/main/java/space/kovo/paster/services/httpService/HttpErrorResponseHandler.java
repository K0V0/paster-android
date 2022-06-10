package space.kovo.paster.services.httpService;

import space.kovo.paster.dtos.ErrorResponseDTO;

public interface HttpErrorResponseHandler {

    void onError(ErrorResponseDTO data);
}
