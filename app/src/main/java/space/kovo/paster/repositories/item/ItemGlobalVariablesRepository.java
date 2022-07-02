package space.kovo.paster.repositories.item;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.repositories.GlobalVariables;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemGlobalVariablesRepository implements ItemRepository {
    @Override
    public List<ItemResponseDTO> getAll() {
        return GlobalVariables.getItems().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemResponseDTO> findLatest() {
        return Optional.ofNullable(GlobalVariables.getItems())
                .filter(items -> items.size() > 0)
                .map(items -> items.get(items.size() - 1));
    }

    @Override
    public ItemResponseDTO getLatest() {
        return findLatest().orElse(null);
    }

    @Override
    public void saveAll(List<ItemResponseDTO> items) {
        GlobalVariables.setItems(items);
    }

    @Override
    public Optional<ItemResponseDTO> findById(long itemId) {
        return Optional.ofNullable(GlobalVariables.getItems())
                .map(items -> items.stream())
                .orElseGet(() -> Stream.empty())
                .filter(item -> item.getId() == itemId)
                .findFirst();
    }

    private static final Comparator<ItemResponseDTO> comparator = (f2, f1) -> {
        if (f1.getTimestamp() > f2.getTimestamp()) {
            return 1;
        } else if (f1.getTimestamp() < f2.getTimestamp()) {
            return -1;
        } else {
            if (f1.getId() == f2.getId()) { return 0; }
            return f1.getId() < f2.getId() ? 1 : -1;
        }
    };
}
