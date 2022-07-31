package space.kovo.paster.repositories.item;

import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.base.GlobalVariables;

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
        return Optional.ofNullable(this.getAll())
                .filter(items -> !items.isEmpty())
                .map(items -> items.get(0));
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

    @Override
    public boolean existByText(String text) {
        return Optional.ofNullable(this.getAll())
                .map(items -> items.stream().anyMatch(item -> item.getText().equals(text)))
                .orElse(false);
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
