package space.kovo.paster.activities.loginActivity;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import space.kovo.paster.R;
import space.kovo.paster.dtos.loginDto.LoginErrorResponseDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;
import space.kovo.paster.services.loginService.LoginResponseHandler;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;

public class LoginActitivityActions {
    private final LoginService loginService;
    private final Context context;

    public LoginActitivityActions(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
    }

    public void registerSubmitListener() {
        Button button = ((LoginActivity) context).findViewById(R.id.loginButton);
        button.setOnClickListener(v -> {
            try {
                login();
            } catch (JSONException e) {
                System.out.println("login attempt exception");
            }
        });
    }

    private void login() throws JSONException {
        loginService.logIn(
                ((EditText) ((LoginActivity) context).findViewById(R.id.loginUserName)).getText().toString(),
                ((EditText) ((LoginActivity) context).findViewById(R.id.loginPassword)).getText().toString());
        loginService.on(new LoginResponseHandler() {
            @Override
            public void success(LoginResponseDTO response) {
                System.out.println("login success");
                System.out.println(response);
            }
            @Override
            public void fail(LoginErrorResponseDTO response) {
                System.out.println("login failed");
            }
        });
    }
}
