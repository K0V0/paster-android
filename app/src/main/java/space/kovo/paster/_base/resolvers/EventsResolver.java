package space.kovo.paster._base.resolvers;

import android.content.Context;
import space.kovo.paster._base.eventBus.Event;

import java.util.Optional;

public abstract class EventsResolver {

    protected final Context context;

    protected EventsResolver(Context context) {
        this.context = context;
    }

    public abstract void handle(Object event);

    protected <EVENT, HANDLER, EVENT_DATA> void runAction(EVENT event, HANDLER handler) {
        Optional.ofNullable(event)
                .filter(evt -> handler != null)
                .filter(evt -> evt instanceof Event)
                .map(evt -> (Event<EVENT_DATA>) event)
                .map(evt -> evt.getData())
                .ifPresent(data -> ((EventsResolverEventHandler<EVENT_DATA>) handler).apply(data));
    }
}
