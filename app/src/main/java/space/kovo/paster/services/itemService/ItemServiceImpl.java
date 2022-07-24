package space.kovo.paster.services.itemService;

import android.content.Context;
import org.json.JSONException;
import space.kovo.paster.dtos.itemDto.ItemRequestDTO;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.services.httpService.HttpRequestService;
import space.kovo.paster.services.httpService.HttpRequestServiceImpl;
import space.kovo.paster.services.jwtService.JwtService;
import space.kovo.paster.services.jwtService.JwtServiceImpl;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;
import space.kovo.paster.services.websocketService.WebsocketService;
import space.kovo.paster.services.websocketService.WebsocketServiceImpl;

public class ItemServiceImpl implements ItemService {
    private static final String GET_ITEMS_ENDPOINT_URL = "https://api.paster.cloud/api/v1/board/items";
    private static final String POST_ITEM_ENDPOINT_URL = "https://api.paster.cloud/api/v1/board/item";
    private final Context context;
    private final HttpRequestService<ItemRequestDTO, ItemsResponseDTO> httpRequestService;
    private final WebsocketService websocketService;
    private final JwtService jwtService;
    private final SharedPreferencesService sharedPreferencesService;
    private ItemResponseHandler itemResponseHandler;

    public ItemServiceImpl(Context context) {
        this.context = context;
        this.httpRequestService = new HttpRequestServiceImpl<>(context, ItemsResponseDTO.class);
        this.websocketService = new WebsocketServiceImpl(context);
        this.jwtService = new JwtServiceImpl();
        this.sharedPreferencesService = new SharedPreferencesServiceImpl(context);
        this.inits();
    }

    @Override
    public void startWebsocketWatchdog() {
        websocketService.onTrigger(this::getItemsFromServer);
    }

    @Override
    public void loadItems() throws JSONException {
        this.getItemsFromServer();
    }

    @Override
    public void sendItem(String text) throws JSONException {
        ItemRequestDTO itemRequestDTO = new ItemRequestDTO();
        itemRequestDTO.setText(text);
        this.sendItemToServer(itemRequestDTO);
    }

    @Override
    public void deleteItem(long itemId) throws JSONException {
        this.deleteItemFromServer(itemId);
    }

    @Override
    public void on(ItemResponseHandler itemResponseHandler) { this.itemResponseHandler = itemResponseHandler; }

    private void inits() {
        httpRequestService.addHeader(
                "Authorization",
                jwtService.prefixizeToken(sharedPreferencesService.getString("jwtToken")));
    }

    private void getItemsFromServer() throws JSONException {
        httpRequestService.getRequest(GET_ITEMS_ENDPOINT_URL);
        httpRequestService.onSuccess(data -> itemResponseHandler.success(data));
        httpRequestService.onError(data -> itemResponseHandler.fail(data));
    }

    private void sendItemToServer(ItemRequestDTO itemRequestDTO) throws JSONException {
        //TODO skumat uspesnost odoslania na server
        httpRequestService.postRequest(POST_ITEM_ENDPOINT_URL, itemRequestDTO);
        httpRequestService.onSuccess(data -> {});
        httpRequestService.onError(data -> {});
    }

    private void deleteItemFromServer(long itemId) throws JSONException {
        httpRequestService.deleteRequest(String.format("%s/%s", POST_ITEM_ENDPOINT_URL, itemId));
        //TODO handle errors
        httpRequestService.onSuccess(data -> {});
        httpRequestService.onError(data -> {});
    }
}
