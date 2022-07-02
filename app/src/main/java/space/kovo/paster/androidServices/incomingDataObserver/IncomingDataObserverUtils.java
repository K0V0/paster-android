package space.kovo.paster.androidServices.incomingDataObserver;

import android.content.Context;
import org.greenrobot.eventbus.EventBus;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;

public class IncomingDataObserverUtils {

    static void processResults(ItemsResponseDTO results, ItemRepository itemRepository) {
        itemRepository.saveAll(results.getItems());
    }

    static void serveClipboard(ItemsResponseDTO results, ItemRepository itemRepository, ClipboardService clipboardService) {
        //TODO vazny problem navrhu
        // ak bolo zariadenie pretym offline a v clipboarde je novsi obsah, bude premaznuty
        // pridat check podla timestamp alebo ina metoda
        itemRepository
                .findLatest()
                .ifPresent(item -> clipboardService.addToClipboard(item.getText()));
    }

    static void notifyViews(Context context) {
        IncomingDataObserverEvent incomingDataObserverEvent = new IncomingDataObserverEvent();
        //TODO analyza ci ma naozaj nove data alebo len prisli tie iste
        incomingDataObserverEvent.setHasNewData(true);
        EventBus.getDefault().post(incomingDataObserverEvent);
    }

}
