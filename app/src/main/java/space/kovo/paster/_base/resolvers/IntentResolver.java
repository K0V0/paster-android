package space.kovo.paster._base.resolvers;

import android.content.Context;
import android.content.Intent;

public abstract class IntentResolver {

    protected final Context context;
    protected final Intent intent;

    protected IntentResolver(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public abstract void handle();
}
