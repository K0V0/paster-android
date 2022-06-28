package space.kovo.paster.activities.activity;

import android.content.Context;
import space.kovo.paster.R;
import space.kovo.paster.ui.dialog.Dialog;
import space.kovo.paster.ui.dialog.DialogImpl;

public class BaseActivityActions {
    private final Context context;
    private final Dialog dialog;

    public BaseActivityActions(Context context) {
        this.context = context;
        this.dialog = new DialogImpl(context);
    }

    public void showDialogIfInternetNoneOrPoor() {
        dialog.show(R.string.app_no_internet_title, R.string.app_no_internet_text);
    }
}
