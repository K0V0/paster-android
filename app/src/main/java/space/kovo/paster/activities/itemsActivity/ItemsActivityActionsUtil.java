package space.kovo.paster.activities.itemsActivity;

import android.annotation.SuppressLint;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ItemsActivityActionsUtil {

    private ItemsActivityActionsUtil() {}

    @SuppressLint("NotifyDataSetChanged")
    static void syncData(List<ItemResponseDTO> presentData, List<ItemResponseDTO> incomingData, ItemsAdapter dataAdapter) {
        if (presentData.isEmpty() && !incomingData.isEmpty()) {
            // new presentData, first run
            presentData.addAll(incomingData);
            dataAdapter.notifyDataSetChanged();
        } else {
            // handle presentData added
            List<Integer> presentDataHashCodes = presentData.stream()
                    .map(ItemResponseDTO::hashCode)
                    .collect(Collectors.toList());
            List<ItemResponseDTO> dataToAdd = IntStream
                    .range(0, incomingData.size())
                    .filter(i -> !presentDataHashCodes.contains(incomingData.get(i).hashCode()))
                    .mapToObj(incomingData::get)
                    .collect(Collectors.toList());
            if (!dataToAdd.isEmpty()) {
                presentData.addAll(dataToAdd);
                dataAdapter.notifyItemRangeInserted(presentData.size(), dataToAdd.size());
            }
            // handle presentData removed
            List<Integer> incomingDataHashCodes = incomingData.stream()
                    .map(ItemResponseDTO::hashCode)
                    .collect(Collectors.toList());
            IntStream.range(0, presentData.size() - 1)
                    .forEach(i -> {
                        if (!incomingDataHashCodes.contains(presentData.get(i).hashCode())) {
                            presentData.remove(i);
                            dataAdapter.notifyItemRemoved(i);
                            //dataAdapter.notifyItemRangeChanged(0, );
                        }
                    });
        }
    }

}
