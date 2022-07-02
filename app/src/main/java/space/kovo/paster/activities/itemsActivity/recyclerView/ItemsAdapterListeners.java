package space.kovo.paster.activities.itemsActivity.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import space.kovo.paster.R;
import space.kovo.paster.utils.Logging;

public class ItemsAdapterListeners {
    private final Context context;

    public ItemsAdapterListeners(Context context) {
        this.context = context;
    }

    public View.OnClickListener copyToClipboardListener() {
        return view -> {
            long itemId = Long.parseLong(((TextView) view.findViewById(R.id.itemIdentificator)).getText().toString());
            Logging.log("itemsAdapterListenters: copyToClipboardListener():", String.format("item id: '%d'", itemId));
            // send event
            EventBus.getDefault().post(new ItemsAdapterSetToClipboardEvent(itemId));
            // animate item
            Animation pressAnimation = new AlphaAnimation(1.0f, 0.5f);
            pressAnimation.setDuration(500);
            view.startAnimation(pressAnimation);
        };
    }
}
