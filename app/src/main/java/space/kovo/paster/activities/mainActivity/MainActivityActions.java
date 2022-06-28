package space.kovo.paster.activities.mainActivity;

import android.content.Context;
import android.content.Intent;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.loginActivity.LoginActivity;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;

public class MainActivityActions {
    private final ConnectivityService connectivityService;
    private final LoginService loginService;
    private final Context context;

    public MainActivityActions(Context context) {
        this.context = context;
        this.connectivityService = new ConnectivityServiceImpl(context);
        this.loginService = new LoginServiceImpl(context);
    }

    public void decideActionAfterStart() {
        if (loginService.isLoggedIn()) {
            startItemsActivity();
        } else {
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private void startItemsActivity() {
        Intent intent = new Intent(context, ItemsActivity.class);
        context.startActivity(intent);
    }
}
