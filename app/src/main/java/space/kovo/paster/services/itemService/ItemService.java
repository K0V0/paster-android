package space.kovo.paster.services.itemService;

public interface ItemService {

    void loadItems();

    void on(ItemResponseHandler itemResponseHandler);
}
