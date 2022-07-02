package space.kovo.paster.activities.itemsActivity.recyclerView;

public class ItemsAdapterSetToClipboardEvent {
    private long itemId;

    public ItemsAdapterSetToClipboardEvent() {}

    public ItemsAdapterSetToClipboardEvent(long itemId) {
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
