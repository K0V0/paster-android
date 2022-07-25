package space.kovo.paster.androidServices.incomingDataObserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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
import space.kovo.paster.utils.Logging;

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
        Logging.log("backgroundService: IncomingDataObserver", "created");
        this.itemService = new ItemServiceImpl(getApplicationContext());
        this.itemRepository = new ItemGlobalVariablesRepository();
        this.clipboardService = new ClipboardServiceImpl(getApplicationContext());
        inits();
        try {
            itemService.loadItems();
            itemService.startWebsocketWatchdog();
        } catch (JSONException e) {
            //throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logging.log("backgroundService: IncomingDataObserver", "binded");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logging.log("backgroundService: IncomingDataObserver", "started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logging.log("backgroundService: IncomingDataObserver", "destroyed");
    }

    private void inits() {
        itemService.on(new ItemResponseHandler() {
            @Override
            public void success(ItemsResponseDTO itemsResponseDTO) {
                Logging.log("backgroundService: IncomingDataObserver", "itemService: data loaded");
                processResults(itemsResponseDTO, itemRepository);
                boolean hasNewData = serveClipboard(itemsResponseDTO, itemRepository, clipboardService);
                if (hasNewData) {
                    Logging.log("backgroundService: IncomingDataObserver", "itemService: data are new");
                    notifyViews();
                }
            }
            @Override
            public void fail(ErrorResponseDTO itemErrorResponseDTO) {
                Logging.log("backgroundService: IncomingDataObserver", "itemService: data loading failed");
            }
        });
    }

}
