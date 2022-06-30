package space.kovo.paster.services.clipboardService;

public interface ClipboardService {

    void addToClipboard(String text);

    String getCurrentText();
}
