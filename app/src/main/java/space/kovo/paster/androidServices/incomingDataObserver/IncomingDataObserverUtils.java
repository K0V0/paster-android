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

    static boolean serveClipboard(ItemsResponseDTO results, ItemRepository itemRepository, ClipboardService clipboardService) {
        //TODO vazny problem navrhu
        // ak bolo zariadenie pretym offline a v clipboarde je novsi obsah, bude premaznuty
        // pridat check podla timestamp alebo ina metoda
        return itemRepository
                .findLatest()
                .map(item -> item.getText())
                .filter(text -> !clipboardService.isCurrent(text))
                .map(text -> {
                    clipboardService.addToClipboard(text);
                    return true;
                })
                .orElse(false);
    }

    static void notifyViews() {
        EventBus.getDefault().post(new IncomingDataObserverEvent(true));
    }

}
