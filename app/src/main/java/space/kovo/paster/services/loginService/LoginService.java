package space.kovo.paster.services.loginService;

import org.json.JSONException;

public interface LoginService {

    boolean isLoggedIn();
    void on(LoginResponseHandler loginResponseHandler);
    void logIn(String userName, String password) throws JSONException;

}
