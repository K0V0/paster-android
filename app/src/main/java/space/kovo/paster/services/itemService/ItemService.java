package space.kovo.paster.services.itemService;

import org.json.JSONException;

public interface ItemService {

    void startWebsocketWatchdog();
    void loadItems() throws JSONException;
    void sendItem(String text) throws JSONException;

    void deleteItem(long itemId) throws JSONException;

    void on(ItemResponseHandler itemResponseHandler);
}
