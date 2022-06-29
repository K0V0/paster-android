package space.kovo.paster.repositories.item;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<ItemResponseDTO> getAll();

    Optional<ItemResponseDTO> findLatest();

    ItemResponseDTO getLatest();

    void saveAll(List<ItemResponseDTO> items);
}
