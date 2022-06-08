package space.kovo.paster.activities.itemsActivity;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.List;

public final class ItemsActivityActionsUtil {

    private ItemsActivityActionsUtil() {}

    static void syncData(List<ItemResponseDTO> data, List<ItemResponseDTO> freshData, ItemsAdapter dataAdapter) {
        if (data.isEmpty() && !freshData.isEmpty()) {
            // new data, first run
            data.addAll(freshData);
            dataAdapter.notifyDataSetChanged();
        } else {
            // handle data removed
            // handle data added
        }
    }

}
