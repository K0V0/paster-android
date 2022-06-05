package space.kovo.paster.activities.itemsActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import space.kovo.paster.R;

public class ItemsActivity extends AppCompatActivity {

    private ItemsActivityActions actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        this.actions = new ItemsActivityActions(this);
        actions.loadItems();
    }
}