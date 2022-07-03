package space.kovo.paster.androidServices.clipboardObserver;

public class ClipboardObserverEvent {
    private String text;

    public ClipboardObserverEvent() {}

    public ClipboardObserverEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
