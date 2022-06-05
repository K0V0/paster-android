package space.kovo.paster.services.loginService;

import space.kovo.paster.dtos.loginDto.LoginErrorResponseDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;

public interface LoginResponseHandler {

    void success(LoginResponseDTO data);
    void fail(LoginErrorResponseDTO data);

}
