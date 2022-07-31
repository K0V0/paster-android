package space.kovo.paster.activities.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.loginActivity.LoginActivity;
import space.kovo.paster.services.connectivityService.ConnectivityService;
import space.kovo.paster.services.connectivityService.ConnectivityServiceImpl;
import space.kovo.paster.services.loginService.LoginService;
import space.kovo.paster.services.loginService.LoginServiceImpl;
import space.kovo.paster.utils.Logging;

import java.util.Optional;

public class MainActivityActions {
    private final LoginService loginService;
    private final Context context;

    public MainActivityActions(Context context) {
        this.context = context;
        this.loginService = new LoginServiceImpl(context);
    }

    public void decideActionAfterStart() {
        Optional.ofNullable(((Activity)context).getIntent())
                .filter(i -> i.getAction().equals("close_app"))
                .ifPresent(i -> {
                    ((Activity)context).finishAffinity();
                    System.exit(0);
                });
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
        getDataFromShareActionResult().ifPresent(text -> intent.putExtra("newItem", text));
        context.startActivity(intent);
    }

    private Optional<String> getDataFromShareActionResult() {
        return Optional.ofNullable(((Activity)context).getIntent())
                .filter(i -> i.getAction().equals("android.intent.action.SEND"))
                .filter(i -> i.getType() != null)
                .filter(i -> i.getType().equals("text/plain"))
                .map(i -> i.getStringExtra("android.intent.extra.TEXT"));
    }
}
