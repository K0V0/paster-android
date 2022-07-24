package space.kovo.paster.activities.itemsActivity.eventsResolver.handlers;

import space.kovo.paster._base.resolvers.EventsResolverEventHandler;

public interface ItemsActivityItemDeleteEventHandler extends EventsResolverEventHandler<Long> {

    void apply(Long itemId);
}
