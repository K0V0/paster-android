package space.kovo.paster.services.httpRequestService;

import space.kovo.paster.dtos.ErrorResponseDTO;

public interface HttpErrorResponseHandler {

    void onError(ErrorResponseDTO data);
}
