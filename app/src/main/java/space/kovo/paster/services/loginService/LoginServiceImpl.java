package space.kovo.paster.services.loginService;

import android.content.Context;
import org.json.JSONException;
import space.kovo.paster.dtos.loginDto.LoginRequestDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;
import space.kovo.paster.services.httpService.HttpRequestService;
import space.kovo.paster.services.httpService.HttpRequestServiceImpl;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {

    // TODO move somewhere to file with setting
    private static final String API_LOGIN_ENDPOINT = "https://api.paster.cloud/api/v1/user/login";
    private static final String TOKEN_KEY_NAME = "jwtToken";
    private final Context context;
    private final HttpRequestService<LoginRequestDTO, LoginResponseDTO> httpRequestService;
    private final SharedPreferencesService sharedPreferencesService;
    private LoginResponseHandler loginResponseHandler;

    public LoginServiceImpl(Context context) {
        this.context = context;
        this.httpRequestService = new HttpRequestServiceImpl<>(context, LoginResponseDTO.class);
        this.sharedPreferencesService = new SharedPreferencesServiceImpl(context);
    }

    @Override
    public boolean isLoggedIn() {
        return Optional.ofNullable(sharedPreferencesService.getString(TOKEN_KEY_NAME))
                .map(token -> !token.trim().equals(""))
                .orElse(false);
        //return false; //FIXME hack to get to login screen during testing
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
        httpRequestService.onSuccess(data -> {
            sharedPreferencesService.save(TOKEN_KEY_NAME, data.getJwtToken());
            loginResponseHandler.success(data);
        });
        httpRequestService.onError(data -> {
            invalidateUser();
            loginResponseHandler.fail(data);
        });
    }

    @Override
    public void invalidateUser() {
        sharedPreferencesService.destroy(TOKEN_KEY_NAME);
    }
}
