package space.kovo.paster.services.loginService;

import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;

public interface LoginResponseHandler {

    void success(LoginResponseDTO data);
    void fail(ErrorResponseDTO data);

}
