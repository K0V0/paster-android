package space.kovo.paster.activities.itemsActivity.uiActionsResolver;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import space.kovo.paster.R;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.itemsActivity.uiActionsResolver.handlers.OnManualContentSubmit;

public class UiActionsResolver {

    private final Context context;
    private final ItemsActivity activity;

    private OnManualContentSubmit onManualContentSubmit;

    public UiActionsResolver(Context context) {
        this.context = context;
        this.activity = (ItemsActivity) context;
    }

    public void handle() {
        this.handleFormSubmit();
    }

    public void setOnManualContentSubmit(OnManualContentSubmit onManualContentSubmit) {
        this.onManualContentSubmit = onManualContentSubmit;
    }

    private void handleFormSubmit() {
        Button button = activity.findViewById(R.id.submit);
        button.setOnClickListener(view -> {
            onManualContentSubmit.apply(((EditText) activity.findViewById(R.id.itemsInput)).getText().toString());
        });
    }
}
