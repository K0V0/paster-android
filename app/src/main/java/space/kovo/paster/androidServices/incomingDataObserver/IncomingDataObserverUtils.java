package space.kovo.paster.androidServices.incomingDataObserver;

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
        // pripadne nech appka zacne pracovat az vtedy, kedy bolo do cloudu vlozene nieco az po jej spusteni
        // pripadne explicitne vyziadany refresh
        itemRepository
                .findLatest()
                .map(item -> item.getText())
                .filter(text -> !clipboardService.isCurrent(text))
                .ifPresent(text -> clipboardService.addToClipboard(text));
    }

    static void notifyViews() {
        EventBus.getDefault().post(new IncomingDataObserverEvent(true));
    }

}
