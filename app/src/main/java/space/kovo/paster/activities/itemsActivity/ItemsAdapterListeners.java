package space.kovo.paster.activities.itemsActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import space.kovo.paster.R;
import space.kovo.paster.utils.Logging;

public class ItemsAdapterListeners {
    private final Context context;

    public ItemsAdapterListeners(Context context) {
        this.context = context;
    }

    public View.OnClickListener copyToClipboardListener() {
        return view -> {
            // send broadcast
            Long itemId = Long.valueOf(((TextView) view.findViewById(R.id.itemIdentificator)).getText().toString());
            Logging.log("itemsAdapterListenters: copyToClipboardListener():", String.format("item id: '%d'", itemId));
            Intent intent = new Intent();
            intent.setAction("itemsActivityActions.clipboard.insert");
            intent.putExtra("idOfClipboardItem", itemId);
            context.sendBroadcast(intent);
            // animate item
            Animation pressAnimation = new AlphaAnimation(1.0f, 0.5f);
            pressAnimation.setDuration(500);
            view.startAnimation(pressAnimation);
        };
    }
}
