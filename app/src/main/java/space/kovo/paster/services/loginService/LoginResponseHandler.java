package space.kovo.paster.services.loginService;

import org.json.JSONObject;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;

public interface LoginResponseHandler {

    void success(LoginResponseDTO jsonObject);
    void fail();

}
