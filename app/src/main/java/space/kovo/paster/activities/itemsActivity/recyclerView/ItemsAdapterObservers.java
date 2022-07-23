package space.kovo.paster.activities.itemsActivity.recyclerView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import space.kovo.paster.utils.Logging;

public class ItemsAdapterObservers extends RecyclerView.AdapterDataObserver {

    private final RecyclerView recyclerView;

    public ItemsAdapterObservers(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onChanged() {
        Logging.log("ItemsAdapterObserver: onChanged()", "scroll to top");
        this.recyclerView.scrollToPosition(0);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        this.onChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        this.onChanged();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        this.onChanged();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        this.onChanged();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        this.onChanged();
    }
}
