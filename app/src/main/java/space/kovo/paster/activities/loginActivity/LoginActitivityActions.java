package space.kovo.paster.activities.loginActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import space.kovo.paster.R;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.dtos.loginDto.LoginErrorResponseDTO;
import space.kovo.paster.dtos.loginDto.LoginResponseDTO;
import space.kovo.paster.services.loginService.LoginResponseHandler;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;
import space.kovo.paster.ui.dialog.Dialog;
import space.kovo.paster.ui.dialog.DialogImpl;

public class LoginActitivityActions {
    private final LoginService loginService;
    private final Context context;
    private final Dialog dialog;

    public LoginActitivityActions(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
        this.dialog = new DialogImpl(context);
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
                startItemsActivity();
            }
            @Override
            public void fail(LoginErrorResponseDTO response) {
                dialog.show(context.getResources().getString(R.string.login_fail_dialog_title),
                        "tui bude text rozobrany z chybovej hlasky z backendu");
            }
        });
    }

    private void startItemsActivity() {
        Intent intent = new Intent(context, ItemsActivity.class);
        context.startActivity(intent);
    }
}
