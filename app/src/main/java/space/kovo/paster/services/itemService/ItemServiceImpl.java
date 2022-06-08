package space.kovo.paster.services.itemService;

import android.content.Context;
import org.json.JSONException;
import space.kovo.paster.dtos.itemDto.ItemRequestDTO;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.services.httpRequestService.HttpRequestService;
import space.kovo.paster.services.httpRequestService.HttpRequestServiceImpl;
import space.kovo.paster.services.jwtService.JwtService;
import space.kovo.paster.services.jwtService.JwtServiceImpl;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;

public class ItemServiceImpl implements ItemService {
    private static final String ENDPOINT_URL = "https://api.paster.cloud/api/v1/board/items";

    private final Context context;

    private final HttpRequestService<ItemRequestDTO, ItemsResponseDTO> httpRequestService;
    private final JwtService jwtService;
    private final SharedPreferencesService sharedPreferencesService;
    private ItemResponseHandler itemResponseHandler;

    public ItemServiceImpl(Context context) {
        this.context = context;
        this.httpRequestService = new HttpRequestServiceImpl<>(context, ItemsResponseDTO.class);
        this.jwtService = new JwtServiceImpl();
        this.sharedPreferencesService = new SharedPreferencesServiceImpl(context);
    }

    @Override
    public void loadItems() throws JSONException {
        httpRequestService.addHeader(
                "Authorization",
                jwtService.prefixizeToken(sharedPreferencesService.getString("jwtToken")));
        httpRequestService.getRequest(ENDPOINT_URL);
        httpRequestService.onSuccess(data -> itemResponseHandler.success(data));
        httpRequestService.onError(data -> itemResponseHandler.fail(data));
    }

    @Override
    public void on(ItemResponseHandler itemResponseHandler) { this.itemResponseHandler = itemResponseHandler; }
}
