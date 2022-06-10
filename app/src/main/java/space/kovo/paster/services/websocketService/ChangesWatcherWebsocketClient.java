package space.kovo.paster.services.websocketService;

import org.json.JSONException;
import tech.gusavila92.websocketclient.WebSocketClient;

import java.net.URI;

public class ChangesWatcherWebsocketClient extends WebSocketClient {

    private ChangeTriggerHandler changeTriggerHandler;

    public ChangesWatcherWebsocketClient(String url) {
        super(URI.create(url));
    }

    @Override
    public void onOpen() {}

    @Override
    public void onTextReceived(String message) {
        System.out.println(message);
        try {
            this.changeTriggerHandler.on();
        } catch (JSONException e) {
            //TODO custom exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBinaryReceived(byte[] data) {}

    @Override
    public void onPingReceived(byte[] data) {}

    @Override
    public void onPongReceived(byte[] data) {}

    @Override
    public void onException(Exception e) {}

    @Override
    public void onCloseReceived() {}

    public void onTrigger(ChangeTriggerHandler changeTriggerHandler) {
        this.changeTriggerHandler = changeTriggerHandler;
    }
}
