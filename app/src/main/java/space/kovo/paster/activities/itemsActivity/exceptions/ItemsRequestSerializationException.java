package space.kovo.paster.activities.itemsActivity.exceptions;

public final class ItemsRequestSerializationException extends RuntimeException {
    public ItemsRequestSerializationException() { super("error constricting request for items fetching"); }
}
