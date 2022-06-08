package space.kovo.paster.services.itemService;

import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;

public interface ItemResponseHandler {

    void success(ItemsResponseDTO itemResponseDTO);

    void fail(ErrorResponseDTO itemErrorResponseDTO);
}
