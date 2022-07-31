package space.kovo.paster.activities.itemsActivity.uiActionsResolver;

import android.content.Context;
import android.widget.EditText;
import space.kovo.paster.R;
import space.kovo.paster.base.resolvers.UiActionsResolver;
import space.kovo.paster.activities.itemsActivity.ItemsActivity;
import space.kovo.paster.activities.itemsActivity.uiActionsResolver.handlers.OnManualContentSubmit;

public class ItemsActivityUiActionsResolver extends UiActionsResolver<ItemsActivity> {

    private OnManualContentSubmit onManualContentSubmit;

    public ItemsActivityUiActionsResolver(Context context) {
        super(context, (ItemsActivity) context);
    }

    public void handle() {
        this.handleFormSubmit();
    }

    public void onManualContentSubmit(OnManualContentSubmit onManualContentSubmit) {
        this.onManualContentSubmit = onManualContentSubmit;
    }

    private void handleFormSubmit() {
        super.activity
                .findViewById(R.id.submit)
                .setOnClickListener(view -> onManualContentSubmit.apply(
                        ((EditText) super.activity.findViewById(R.id.itemsInput)).getText().toString()));
    }
}
