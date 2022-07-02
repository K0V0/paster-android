package space.kovo.paster.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

public class DialogImpl implements Dialog {
    private static final String CLOSE_BUTTON_TEXT = "OK";
    private static final int DEFAULT_CLOSE_DELAY_MILISECONDS = 2000;
    private final Context context;
    private AlertDialog alertDialog;
    private String title;
    private String text;
    private int delayMiliseconds;

    public DialogImpl(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(int title) {
        this.title = context.getResources().getString(title);
    }

    public void setText(int text) {
        this.text = context.getResources().getString(text);
    }

    public void setDelayMiliseconds(int delayMiliseconds) {
        this.delayMiliseconds = delayMiliseconds;
    }

    public void show(int title, int text) {
        this.setTitle(title);
        this.setText(text);
        this.show();
    }

    @Override
    public void show(String title, String text) {
        this.setTitle(title);
        this.setText(text);
        this.show();
    }

    @Override
    public void showAndClose() {
        this.showAndClose(DEFAULT_CLOSE_DELAY_MILISECONDS);
    }

    public void show() {
        this.alertDialog = new AlertDialog.Builder(context).create();
        this.alertDialog.setTitle(title);
        this.alertDialog.setMessage(text);
        this.alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                CLOSE_BUTTON_TEXT,
                (dialog, which) -> dialog.dismiss());
        this.alertDialog.show();
    }

    public void showAndClose(int miliSeconds) {
        show();
        new Handler().postDelayed(() -> this.alertDialog.dismiss(), miliSeconds);
    }

}
