package space.kovo.paster.androidServices.clipboardObserver;

import space.kovo.paster._base.eventBus.Event;

public class ClipboardObserverEvent extends Event<String> {

    public ClipboardObserverEvent(String data) {
        super(data);
    }
}
