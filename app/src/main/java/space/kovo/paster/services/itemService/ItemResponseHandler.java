package space.kovo.paster.services.itemService;

import space.kovo.paster.dtos.itemDto.ItemErrorResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

public interface ItemResponseHandler {

    void success(ItemResponseDTO itemResponseDTO);

    void fail(ItemErrorResponseDTO itemErrorResponseDTO);
}
