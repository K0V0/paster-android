package space.kovo.paster.services.itemService;

import org.json.JSONException;
import space.kovo.paster.dtos.itemDto.ItemRequestDTO;

public interface ItemService {

    void startWebsocketWatchdog();
    void loadItems() throws JSONException;
    void sendItem(String text) throws JSONException;

    void on(ItemResponseHandler itemResponseHandler);
}
