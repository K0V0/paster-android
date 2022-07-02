package space.kovo.paster.ui.notification;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class NotificationImpl implements Notification {
    private static final int TOAST_DEFAULT_DURATION = Toast.LENGTH_SHORT;
    private static final int TOAST_DEFAULT_DELAY = 1000;
    private final Context context;
    private String text;
    private int duration = 0;
    private int delay = 0;

    public NotificationImpl(Context context) {
        this.context = context;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setText(int text) {
        this.text = context.getString(text);
    }

    @Override
    public void show() {
        Toast.makeText(context, text, duration).show();
    }

    @Override
    public void show(String text) {
        this.text = text;
        this.show();
    }

    @Override
    public void show(int text) {
        this.setText(text);
        this.show();
    }

    @Override
    public void setDuration(int length) {
        if (length > 1) {
            this.duration = TOAST_DEFAULT_DURATION;
            return;
        }
        this.duration = length;
    }

    @Override
    public void setDelay(int miliSeconds) {
        this.delay = miliSeconds;
    }

    @Override
    public void showWithDelay() {
        new Handler().postDelayed(() -> show(), delay == 0 ? TOAST_DEFAULT_DELAY : delay);
    }

    @Override
    public void showWithDelay(String text) {
        this.setText(text);
        this.showWithDelay();
    }

    @Override
    public void showWithDelay(int text) {
        this.setText(text);
        this.showWithDelay();
    }

}
