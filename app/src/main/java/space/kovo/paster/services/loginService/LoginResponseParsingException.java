package space.kovo.paster.services.loginService;

import org.json.JSONException;

public final class LoginResponseParsingException extends JSONException {

    public LoginResponseParsingException() {
        super("Error parson login response");
    }
}
