package space.kovo.paster.activities.itemsActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import space.kovo.paster.R;

public class ItemsAdapterListeners {
    private final Context context;

    public ItemsAdapterListeners(Context context) {
        this.context = context;
    }

    public View.OnClickListener copyToClipboardListener() {
        return view -> {
            Long itemId = Long.valueOf(((TextView) view.findViewById(R.id.itemIdentificator)).getText().toString());
            Log.d("itemsAdapterListenters: copyToClipboardListener():", String.format("item id: '%d'", itemId));
            Intent intent = new Intent();
            intent.setAction("itemsActivityActions.clipboard.insert");
            intent.putExtra("idOfClipboardItem", itemId);
            context.sendBroadcast(intent);
        };
    }
}
