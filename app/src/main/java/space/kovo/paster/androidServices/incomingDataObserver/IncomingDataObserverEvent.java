package space.kovo.paster.androidServices.incomingDataObserver;

import space.kovo.paster._base.eventBus.Event;

public class IncomingDataObserverEvent extends Event<Boolean> {

    public IncomingDataObserverEvent(Boolean data) {
        super(data);
    }
}
