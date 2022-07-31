package space.kovo.paster.androidServices.clipboardObserver;

import space.kovo.paster.base.eventBus.Event;

public class ClipboardObserverEvent extends Event<String> {

    public ClipboardObserverEvent(String data) {
        super(data);
    }
}
