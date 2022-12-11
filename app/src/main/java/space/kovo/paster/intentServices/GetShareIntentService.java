package space.kovo.paster.intentServices;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import space.kovo.paster.utils.Logging;

import java.util.Optional;


//TODO zaspominat preco vznikla a ci ma byt dokoncena tato servica
public class GetShareIntentService extends IntentService {


    public GetShareIntentService() {
        super("GetShareIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Optional.ofNullable(intent)
                .filter(i -> i.getAction().equals("android.intent.action.SEND"))
                .filter(i -> i.getType() != null)
                .filter(i -> i.getType().equals("text/plain"))
                .map(i -> i.getStringExtra("android.intent.extra.TEXT"))
                .ifPresent(text -> {
                    Logging.log("getShareIntentService: onHandleIntent()", String.format("got some text: %s", text));

                });
    }
}
