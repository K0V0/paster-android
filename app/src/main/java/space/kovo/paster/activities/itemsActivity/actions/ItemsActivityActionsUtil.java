package space.kovo.paster.activities.itemsActivity.actions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import space.kovo.paster.R;
import space.kovo.paster.activities.itemsActivity.recyclerView.ItemsAdapter;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;
import space.kovo.paster.ui.dialog.Dialog;

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

    static void setClipboard(long itemId, ClipboardService clipboardService, ItemRepository itemRepository, Dialog dialog, Context context) {
        itemRepository
                .findById(itemId)
                .ifPresent(item -> {
                    clipboardService.addToClipboard(item.getText());
                    new Handler().postDelayed(() -> {
                        dialog.setTitle(R.string.item_copied_to_clipboard);
                        dialog.setText(item.getPreview().length() < 128 ? item.getPreview() : item.getPreview().substring(0, 128) + "...");
                        dialog.showAndClose();
                    }, POPUP_COPIED_TO_CLIPBOARD_DELAY);
                });
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
