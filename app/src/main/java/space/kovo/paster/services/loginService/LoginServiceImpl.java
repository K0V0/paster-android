package space.kovo.paster.services.loginService;

import android.content.Context;
import org.json.JSONException;
import space.kovo.paster.dtos.loginDto.LoginErrorResponseDTO;
import space.kovo.paster.dtos.loginDto.LoginRequestDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;
import space.kovo.paster.services.httpRequestService.HttpRequestService;
import space.kovo.paster.services.httpRequestService.HttpRequestServiceImpl;
import space.kovo.paster.services.jwtService.JwtService;
import space.kovo.paster.services.jwtService.JwtServiceImpl;

public class LoginServiceImpl implements LoginService {

    // TODO move somewhere to file with setting
    private static final String API_LOGIN_ENDPOINT = "https://api.paster.cloud/api/v1/user/login";
    private final Context context;
    private final HttpRequestService<LoginRequestDTO, LoginResponseDTO, LoginErrorResponseDTO> httpRequestService;
    private final JwtService jwtService;
    private LoginResponseHandler loginResponseHandler;

    public LoginServiceImpl(Context context) {
        this.context = context;
        this.httpRequestService = new HttpRequestServiceImpl<>(context, LoginResponseDTO.class, LoginErrorResponseDTO.class);
        this.jwtService = new JwtServiceImpl();
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void on(LoginResponseHandler loginResponseHandler) {
        this.loginResponseHandler = loginResponseHandler;
    }

    @Override
    public void logIn(String userName, String password) throws JSONException {
        httpRequestService.postRequest(
                API_LOGIN_ENDPOINT,
                new LoginRequestDTO(userName, password));
        httpRequestService.onSuccess(data -> loginResponseHandler.success(data));
        httpRequestService.onError(data -> loginResponseHandler.fail(data));
    }
}
