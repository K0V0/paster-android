package space.kovo.paster.services.clipboardService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardServiceImpl implements ClipboardService {
    private final Context context;
    private String currentText;

    public ClipboardServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addToClipboard(String text) {
        currentText = text;
        ClipboardManager clipboardManager = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("data", text);
        clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public String getCurrentText() {
        return currentText;
    }

}
