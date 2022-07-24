package space.kovo.paster.activities.itemsActivity.intentResolver.handlers;

import space.kovo.paster._base.resolvers.IntentResolverIntentHandler;

public interface OnSharedContentIncomingHandler extends IntentResolverIntentHandler<String> {
    void apply(String text);
}
