package space.kovo.paster.services.websocketService;

import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesService;
import space.kovo.paster.services.sharedPreferencesService.SharedPreferencesServiceImpl;

public class WebsocketServiceImpl implements WebsocketService {
    //TODO move to some safe storage
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int CONNECTION_READ_TIMEOUT = 120000;
    private static final int CONNECTION_RECONNECTION_DELAY = 5000;
    private static final String WEBSOCKET_ENDPOINT = "wss://api.paster.cloud/websocket";
    private static final String API_KEY = "Sv2t75NiOktsxI023mdVA4hvzP0rUhfF";
    private final Context context;
    private final SharedPreferencesService sharedPreferencesService;

    private final ChangesWatcherWebsocketClient changesWatcherWebsocketClient;

    private ChangeTriggerHandler changeTriggerHandler;

    public WebsocketServiceImpl(Context context) {
        this.context = context;
        this.sharedPreferencesService = new SharedPreferencesServiceImpl(context);
        this.changesWatcherWebsocketClient = new ChangesWatcherWebsocketClient(
                String.format("%s?jwtToken=%s&apiKey=%s",
                        WEBSOCKET_ENDPOINT, sharedPreferencesService.getString("jwtToken"), API_KEY));
        inits();
    }

    @Override
    public void onTrigger(ChangeTriggerHandler changeTriggerHandler) {
        this.changeTriggerHandler = changeTriggerHandler;
    }

    private void inits() {
        changesWatcherWebsocketClient.onTrigger(() -> {
            Log.d("websocketService: onTrigger()", "reconnected");
            this.changeTriggerHandler.on();
        });
        changesWatcherWebsocketClient.setConnectTimeout(CONNECTION_TIMEOUT);
        changesWatcherWebsocketClient.setReadTimeout(CONNECTION_READ_TIMEOUT);
        changesWatcherWebsocketClient.enableAutomaticReconnection(CONNECTION_RECONNECTION_DELAY);
        changesWatcherWebsocketClient.connect();
    }

}
