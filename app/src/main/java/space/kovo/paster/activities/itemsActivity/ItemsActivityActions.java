package space.kovo.paster.activities.itemsActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.kovo.paster.R;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.item.ItemGlobalVariablesRepository;
import space.kovo.paster.repositories.item.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static space.kovo.paster.activities.itemsActivity.ItemsActivityActionsUtil.syncData;

public class ItemsActivityActions {

    private final Context context;
    private final ItemRepository itemRepository;

    private final RecyclerView itemsRecyclerView;
    private final ItemsAdapter itemsAdapter;
    private final List<ItemResponseDTO> items;

    public ItemsActivityActions(Context context) {
        this.context = context;
        this.itemRepository = new ItemGlobalVariablesRepository();
        this.items = new ArrayList<>();
        this.itemsAdapter = new ItemsAdapter(context, items);
        this.itemsRecyclerView = ((Activity) context).findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemsRecyclerView.setAdapter(itemsAdapter);
        attachListeners();
    }

    public void refreshItems() {
        Log.d("itemsActivityActions: loadItems() size:", "" + itemRepository.getAll().size());
        syncData(items, itemRepository.getAll(), itemsAdapter);
    }

    private void attachListeners() {
        
    }
}
