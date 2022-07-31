package space.kovo.paster.androidServices.incomingDataObserver;

import space.kovo.paster.base.eventBus.Event;

public class IncomingDataObserverEvent extends Event<Boolean> {

    public IncomingDataObserverEvent(Boolean data) {
        super(data);
    }
}
