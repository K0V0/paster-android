package space.kovo.paster.base.resolvers;

import android.content.Context;

public abstract class UiActionsResolver<ACTIVITY> {

    protected final Context context;
    protected final ACTIVITY activity;

    public UiActionsResolver(Context context, ACTIVITY activity) {
        this.context = context;
        this.activity = activity;
    }
}
