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
import space.kovo.paster.services.websocketService.ChangeTriggerHandler;
import space.kovo.paster.services.websocketService.WebsocketService;
import space.kovo.paster.services.websocketService.WebsocketServiceImpl;

public class ItemServiceImpl implements ItemService {
    private static final String ENDPOINT_URL = "https://api.paster.cloud/api/v1/board/items";
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
        inits();
    }

    @Override
    public void loadItems() throws JSONException {
        makeHttpRequest();
    }

    @Override
    public void on(ItemResponseHandler itemResponseHandler) { this.itemResponseHandler = itemResponseHandler; }

    private void inits() {
        websocketService.onTrigger(this::makeHttpRequest);
    }

    private void makeHttpRequest() throws JSONException {
        httpRequestService.addHeader(
                "Authorization",
                jwtService.prefixizeToken(sharedPreferencesService.getString("jwtToken")));
        httpRequestService.getRequest(ENDPOINT_URL);
        httpRequestService.onSuccess(data -> itemResponseHandler.success(data));
        httpRequestService.onError(data -> itemResponseHandler.fail(data));
    }
}
