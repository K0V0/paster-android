package space.kovo.paster.androidServices.incomingDataObserver;

import android.content.Context;
import android.content.Intent;
import space.kovo.paster.dtos.itemDto.ItemsResponseDTO;
import space.kovo.paster.repositories.item.ItemRepository;
import space.kovo.paster.services.clipboardService.ClipboardService;

public class IncomingDataObserverUtils {

    public static void processResults(ItemsResponseDTO results, ItemRepository itemRepository) {
        itemRepository.saveAll(results.getItems());
    }

    public static void serveClipboard(ItemsResponseDTO results, ItemRepository itemRepository, ClipboardService clipboardService) {
        //TODO vazny problem navrhu
        // ak bolo zariadenie pretym offline a v clipboarde je novsi obsah, bude premaznuty
        // pridat check podla timestamp alebo ina metoda
        itemRepository
                .findLatest()
                .ifPresent(item -> clipboardService.addToClipboard(item.getText()));
    }

    public static void notifyViews(Context context) {
        Intent intent = new Intent();
        intent.setAction("incomingDataObserver.data.new");
        intent.putExtra("haveNewData", true);
        context.sendBroadcast(intent);
    }
}
