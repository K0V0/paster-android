package space.kovo.paster.activities.loginActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import space.kovo.paster.R;

public class LoginActivity extends AppCompatActivity {
    private LoginActitivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        actions = new LoginActitivityActions(this);
        actions.registerSubmitListener();
    }

}