package space.kovo.paster.activities.itemsActivity;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import space.kovo.paster.R;
import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.services.itemService.ItemResponseHandler;
import space.kovo.paster.services.itemService.ItemService;
import space.kovo.paster.services.itemService.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static space.kovo.paster.activities.itemsActivity.ItemsActivityActionsUtil.syncData;

public class ItemsActivityActions {

    private final Context context;
    private final ItemService itemService;

    private final RecyclerView itemsRecyclerView;
    private final ItemsAdapter itemsAdapter;
    private final List<ItemResponseDTO> items;

    public ItemsActivityActions(Context context) {
        this.context = context;
        this.itemService = new ItemServiceImpl(context);
        this.items = new ArrayList<>();
        this.itemsAdapter = new ItemsAdapter(context, items);
        this.itemsRecyclerView = ((Activity) context).findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemsRecyclerView.setAdapter(itemsAdapter);
    }

    public void loadItems() {
        try {
            itemService.loadItems();
        } catch (JSONException e) {
            throw new ItemsRequestSerializationException();
        }
        itemService.on(new ItemResponseHandler() {
            @Override
            public void success(ItemsResponseDTO itemsResponseDTO) {
                syncData(items, itemsResponseDTO.getItems(), itemsAdapter);
            }
            @Override
            public void fail(ErrorResponseDTO errorResponseDTO) {
                System.out.println(errorResponseDTO);
            }
        });
    }
}
