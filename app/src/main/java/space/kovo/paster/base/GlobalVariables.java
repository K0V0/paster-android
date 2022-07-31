package space.kovo.paster.base;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    private static final List<ItemResponseDTO> items = new ArrayList<>();

    public static List<ItemResponseDTO> getItems() {
        return items;
    }

    public static void setItems(List<ItemResponseDTO> items) {
        GlobalVariables.items.clear();
        GlobalVariables.items.addAll(items);
    }
}
