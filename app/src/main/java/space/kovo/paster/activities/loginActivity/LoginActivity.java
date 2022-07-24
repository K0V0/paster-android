package space.kovo.paster.activities.loginActivity;

import android.os.Bundle;
import space.kovo.paster.R;
import space.kovo.paster._base.activity.BaseActivity;

public class LoginActivity extends BaseActivity {
    private LoginActitivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        actions = new LoginActitivityActions(this);
        actions.registerSubmitListener();
    }

}