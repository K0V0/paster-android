package space.kovo.paster.ui.dialog;

public interface Dialog {

    void show(String title, String text);
    void show(int title, int text);
    void show();
    void showAndClose(int miliSeconds);
    void showAndClose();
    void setTitle(String title);
    void setText(String text);
    void setTitle(int title);
    void setText(int text);
    void setDelayMiliseconds(int delay);

}
