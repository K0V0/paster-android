package space.kovo.paster.androidServices.incomingDataObserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import org.json.JSONException;
import space.kovo.paster.dtos.ErrorResponseDTO;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.repositories.item.ItemGlobalVariablesRepository;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;
import space.kovo.paster.services.clipboardService.ClipboardServiceImpl;
import space.kovo.paster.services.itemService.ItemResponseHandler;
import space.kovo.paster.services.itemService.ItemService;
import space.kovo.paster.services.itemService.ItemServiceImpl;

import static space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverUtils.notifyViews;
import static space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverUtils.processResults;
import static space.kovo.paster.androidServices.incomingDataObserver.IncomingDataObserverUtils.serveClipboard;

public class IncomingDataObserver extends Service {

    private IncomingDataObserverBinder binder = new IncomingDataObserverBinder();

    private ItemService itemService;
    private ItemRepository itemRepository;
    private ClipboardService clipboardService;

    public class IncomingDataObserverBinder extends Binder {
        public IncomingDataObserver getIncomingDataObserver() {
            return IncomingDataObserver.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d("backgroundService: IncomingDataObserver", "created");
        this.itemService = new ItemServiceImpl(getApplicationContext());
        this.itemRepository = new ItemGlobalVariablesRepository();
        this.clipboardService = new ClipboardServiceImpl(getApplicationContext());
        inits();
        try {
            itemService.loadItems();
        } catch (JSONException e) {
            //throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("backgroundService: IncomingDataObserver", "binded");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("backgroundService: IncomingDataObserver", "started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("backgroundService: IncomingDataObserver", "destroyed");
    }

    private void inits() {
        itemService.on(new ItemResponseHandler() {
            @Override
            public void success(ItemsResponseDTO itemsResponseDTO) {
                Log.d("backgroundService: IncomingDataObserver", "itemService: data loaded");
                processResults(itemsResponseDTO, itemRepository);
                serveClipboard(itemsResponseDTO, itemRepository, clipboardService);
                notifyViews(getApplicationContext());
            }
            @Override
            public void fail(ErrorResponseDTO itemErrorResponseDTO) {
                Log.d("backgroundService: IncomingDataObserver", "itemService: data loading failed");
            }
        });
    }

}
