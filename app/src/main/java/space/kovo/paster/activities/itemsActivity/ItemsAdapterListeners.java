package space.kovo.paster.activities.itemsActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import space.kovo.paster.R;

public final class ItemsAdapterListeners {

    public static View.OnClickListener copyToClipboardListener() {
        return view -> {
            Long itemId = Long.valueOf(((TextView) view.findViewById(R.id.itemIdentificator)).getText().toString());
            Log.d("itemsAdapterListenters: copyToClipboardListener():", String.format("item id: '%d'", itemId));

            //TODO send broadcast to call clipboard service besause in static class
        };
    }
}
