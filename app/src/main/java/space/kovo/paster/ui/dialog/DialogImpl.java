package space.kovo.paster.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogImpl implements Dialog {
    private Context context;

    public DialogImpl(Context context) {
        this.context = context;
    }

    @Override
    public void show(String title, String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
