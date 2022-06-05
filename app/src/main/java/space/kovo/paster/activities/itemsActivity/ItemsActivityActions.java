package space.kovo.paster.activities.itemsActivity;

import android.content.Context;
import space.kovo.paster.services.itemService.ItemService;
import space.kovo.paster.services.itemService.ItemServiceImpl;

public class ItemsActivityActions {

    private final Context context;
    private final ItemService itemService;

    public ItemsActivityActions(Context context) {
        this.context = context;
        this.itemService = new ItemServiceImpl(context);
    }

    public void loadItems() {

    }
}
