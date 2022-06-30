package space.kovo.paster.activities.itemsActivity;

import android.annotation.SuppressLint;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.services.clipboardService.ClipboardService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

//    public static void syncClipboard(List<ItemResponseDTO> presentData, List<ItemResponseDTO> incomingData, ClipboardService clipboardService) {
//        Optional.ofNullable(incomingData)
//                .filter(inData -> !inData.isEmpty())
//                .map(Collection::stream)
//                .orElseGet(Stream::empty)
//                .filter(presentData::contains)
//                .findFirst()
//                .ifPresent(data -> {
//                    String result = data.getText().trim();
//                    if (!result.equals("")) {
//                        clipboardService.addToClipboard(result);
//                    }
//                });
//    }

}
