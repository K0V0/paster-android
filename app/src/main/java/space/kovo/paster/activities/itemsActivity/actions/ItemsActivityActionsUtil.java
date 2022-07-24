package space.kovo.paster.activities.itemsActivity.actions;

import android.annotation.SuppressLint;
import android.content.Context;
import org.json.JSONException;
import space.kovo.paster.R;
import space.kovo.paster.activities.itemsActivity.recyclerView.ItemsAdapter;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;
import space.kovo.paster.services.itemService.ItemService;
import space.kovo.paster.ui.notification.Notification;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ItemsActivityActionsUtil {
    private static final int POPUP_COPIED_TO_CLIPBOARD_DELAY = 1000;

    @SuppressLint("NotifyDataSetChanged")
    static void syncData(List<ItemResponseDTO> presentData, List<ItemResponseDTO> incomingData, ItemsAdapter dataAdapter) {
        if (presentData.isEmpty() && !incomingData.isEmpty()) {
            // new presentData, first run
            presentData.addAll(incomingData);
            dataAdapter.notifyDataSetChanged();
        } else {
            // handle presentData added
            List<Integer> presentDataHashCodes = getHashCodes(presentData);
            List<ItemResponseDTO> dataToAdd = IntStream
                    .range(0, incomingData.size())
                    .filter(i -> !presentDataHashCodes.contains(incomingData.get(i).hashCode()))
                    .mapToObj(incomingData::get)
                    .collect(Collectors.toList());
            if (!dataToAdd.isEmpty()) {
                dataToAdd.forEach(dt -> {
                    presentData.add(0, dt);
                    dataAdapter.notifyItemInserted(0);
                });
            }
            // handle presentData removed
            List<Integer> incomingDataHashCodes = getHashCodes(incomingData);
            presentData.stream()
                    .filter(pd -> !incomingDataHashCodes.contains(pd.hashCode()))
                    .map(pd -> presentData.indexOf(pd))
                    .sorted(Comparator.reverseOrder())
                    .forEach(pdIndex -> {
                        presentData.remove((int) pdIndex);
                        dataAdapter.notifyItemRemoved((int) pdIndex);
                    });
        }
    }

    static void setClipboard(long itemId, ClipboardService clipboardService, ItemRepository itemRepository, Notification notification, Context context) {
        itemRepository
                .findById(itemId)
                .ifPresent(item -> {
                    String msg = String.format("%s:\n%s",
                            context.getString(R.string.item_copied_to_clipboard),
                            item.getPreview().length() < 128 ? item.getPreview() : item.getPreview().substring(0, 128) + "...");
                    clipboardService.addToClipboard(item.getText());
                    notification.showWithDelay(msg);
                });
    }

    static void sendToServer(String text, ItemService itemService) {
        try {
            itemService.sendItem(text);
        } catch (JSONException e) {
            //TODO mechanizmus na opakovanie podla typu vyhodenej exception
            throw new RuntimeException(e);
        }
    }

    static void deleteFromServer(long itemId, ItemService itemService) {
        try {
            itemService.deleteItem(itemId);
        } catch (JSONException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> getHashCodes(List<ItemResponseDTO> itemResponseDTOs) {
        return itemResponseDTOs.stream()
                .map(ItemResponseDTO::hashCode)
                .collect(Collectors.toList());
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
