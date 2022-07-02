package space.kovo.paster.ui.notification;

public interface Notification {

    void setText(String text);
    void setText(int text);
    void show();
    void show(String text);
    void show(int text);
    void setDuration(int length);
    void setDelay(int miliSeconds);
    void showWithDelay();
    void showWithDelay(String message);
    void showWithDelay(int text);

}
