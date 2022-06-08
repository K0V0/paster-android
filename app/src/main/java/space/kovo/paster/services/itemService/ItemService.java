package space.kovo.paster.services.itemService;

import org.json.JSONException;

public interface ItemService {

    void loadItems() throws JSONException;

    void on(ItemResponseHandler itemResponseHandler);
}
