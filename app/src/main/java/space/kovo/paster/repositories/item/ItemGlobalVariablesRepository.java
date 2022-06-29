package space.kovo.paster.repositories.item;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.GlobalVariables;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemGlobalVariablesRepository implements ItemRepository {
    @Override
    public List<ItemResponseDTO> getAll() {
        return GlobalVariables.getItems().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemResponseDTO> findLatest() {
        return Optional.empty();
    }

    @Override
    public ItemResponseDTO getLatest() {
        return null;
    }

    @Override
    public void saveAll(List<ItemResponseDTO> items) {
        GlobalVariables.setItems(items);
    }

    private static final Comparator<ItemResponseDTO> comparator = (f1, f2) -> {
        if (f1.getTimestamp() > f2.getTimestamp()) {
            return -1;
        } else if (f1.getTimestamp() < f2.getTimestamp()) {
            return 1;
        }
        return 0;
    };
}
