package space.kovo.paster.activities.itemsActivity.eventsResolver.handlers;

import space.kovo.paster._base.resolvers.EventsResolverEventHandler;

public interface ItemsActivityClipboardChangeEventHandler extends EventsResolverEventHandler<String> {
    void apply(String text);
}
