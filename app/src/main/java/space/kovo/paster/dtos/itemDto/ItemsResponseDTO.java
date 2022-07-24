package space.kovo.paster.dtos.itemDto;

import space.kovo.paster._base.dtos.ResponseDTO;

import java.util.List;

public class ItemsResponseDTO extends ResponseDTO {
    private List<ItemResponseDTO> items;

    public List<ItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemResponseDTO> items) {
        this.items = items;
    }
}
