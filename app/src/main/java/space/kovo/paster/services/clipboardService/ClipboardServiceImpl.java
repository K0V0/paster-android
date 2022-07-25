package space.kovo.paster.services.clipboardService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import space.kovo.paster.utils.Logging;

import java.util.Optional;

public class ClipboardServiceImpl implements ClipboardService {
    private final Context context;
    private ClipboardManager clipboardManager;
    private String currentText;
    private ClipboardServiceChangeHandler changeHandler;

    public ClipboardServiceImpl(Context context) {
        this.context = context;
        this.clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        this.inits();
    }

    @Override
    public void addToClipboard(String text) {
        currentText = text;
        ClipData clipData = ClipData.newPlainText("data", text);
        clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public String getCurrentText() {
        return currentText;
    }

    @Override
    public void onAdd(ClipboardServiceChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    public void startListener() {
        Logging.log("clipboardService: startListener()", "clipboard listener attached");
        /** listens for clipboard change text. due security reasons nor avail on newer androids */
        clipboardManager.addPrimaryClipChangedListener(() -> {
            String text = clipboardManager.getText().toString();
            Logging.log("clipboardService: startListener()", "clipboard listener triggered");
            if (!isCurrent(text)) {
                Logging.log("clipboardService: startListener()", "content of clipboard changed");
                this.changeHandler.apply(text);
            }
        });
    }

    public boolean isCurrent(String text) {
        return Optional.ofNullable(this.currentText)
                .map(currText -> currText.equals(text))
                .orElse(false);
    }

    private void inits() {
        /** for security reasons it is not possible to access clipboard from background (on app startup here) */
        this.currentText = Optional.ofNullable(clipboardManager)
                .map(cm -> cm.getPrimaryClip())
                .map(pc -> pc.getItemAt(0))
                .map(i -> i.getText())
                .map(i -> i.toString())
                .orElse(null);
    }

}
