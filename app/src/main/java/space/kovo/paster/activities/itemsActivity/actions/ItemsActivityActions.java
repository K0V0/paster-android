package space.kovo.paster.activities.itemsActivity.actions;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.kovo.paster.R;
import space.kovo.paster.activities.itemsActivity.recyclerView.ItemsAdapter;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.item.ItemGlobalVariablesRepository;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;
import space.kovo.paster.services.clipboardService.ClipboardServiceImpl;
import space.kovo.paster.ui.notification.Notification;
import space.kovo.paster.ui.notification.NotificationImpl;
import space.kovo.paster.utils.Logging;

import java.util.ArrayList;
import java.util.List;

import static space.kovo.paster.activities.itemsActivity.actions.ItemsActivityActionsUtil.setClipboard;
import static space.kovo.paster.activities.itemsActivity.actions.ItemsActivityActionsUtil.syncData;

public class ItemsActivityActions {

    private final Context context;
    private final ItemRepository itemRepository;
    private final ClipboardService clipboardService;
    private final Notification notification;

    private final RecyclerView itemsRecyclerView;
    private final ItemsAdapter itemsAdapter;
    private final List<ItemResponseDTO> items;

    public ItemsActivityActions(Context context) {
        this.context = context;
        this.itemRepository = new ItemGlobalVariablesRepository();
        this.clipboardService = new ClipboardServiceImpl(context);
        this.notification = new NotificationImpl(context);
        this.items = new ArrayList<>();
        this.itemsAdapter = new ItemsAdapter(context, items);
        this.itemsRecyclerView = ((Activity) context).findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    public void refreshItems() {
        Logging.log("itemsActivityActions: refreshItems() size", "" + itemRepository.getAll().size());
        syncData(items, itemRepository.getAll(), itemsAdapter);
    }

    public void sendItemToClipboard(long itemId) {
        Logging.log("itemsActivityActions: sendItemToClipboard()", String.format("%d", itemId));
        setClipboard(itemId, clipboardService, itemRepository, notification, context);
    }

    public void sendItemToServer(String text) {

    }

}
