package space.kovo.paster.services.itemService;

import android.content.Context;
import space.kovo.paster.dtos.itemDto.ItemRequestDTO;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;
import space.kovo.paster.services.httpRequestService.HttpRequestService;
import space.kovo.paster.services.httpRequestService.HttpRequestServiceImpl;
import space.kovo.paster.services.jwtService.JwtService;
import space.kovo.paster.services.jwtService.JwtServiceImpl;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;

public class ItemServiceImpl implements ItemService {

    private final Context context;

    private final HttpRequestService httpRequestService;
    private final JwtService jwtService;
    private final SharedPreferencesService sharedPreferencesService;

    public ItemServiceImpl(Context context) {
        this.context = context;
        this.httpRequestService = new HttpRequestServiceImpl<ItemRequestDTO, ItemResponseDTO>(context, ItemResponseDTO.class);
        this.jwtService = new JwtServiceImpl();
        this.sharedPreferencesService = new SharedPreferencesServiceImpl(context);
    }

    @Override
    public void loadItems() {

    }

    @Override
    public void on(ItemResponseHandler itemResponseHandler) {

    }
}
