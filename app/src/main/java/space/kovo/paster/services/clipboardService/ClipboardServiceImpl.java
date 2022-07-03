package space.kovo.paster.services.clipboardService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import space.kovo.paster.utils.Logging;

public class ClipboardServiceImpl implements ClipboardService {
    private final Context context;
    private ClipboardManager clipboardManager;
    private String currentText;
    private ClipboardServiceChangeHandler changeHandler;

    public ClipboardServiceImpl(Context context) {
        this.context = context;
        this.clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //this.inits();
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
        clipboardManager.addPrimaryClipChangedListener(() -> {
            String text = clipboardManager.getText().toString();
            Logging.log("clipboardService: startListener()", "clipboard listener changed");
            this.changeHandler.apply(text);
        });
    }

}
