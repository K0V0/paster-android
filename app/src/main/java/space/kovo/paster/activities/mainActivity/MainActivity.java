package space.kovo.paster.activities.mainActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import space.kovo.paster.R;

public class MainActivity extends AppCompatActivity {

    private MainActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actions = new MainActivityActions(this);
        actions.decideActionAfterStart();
    }

}