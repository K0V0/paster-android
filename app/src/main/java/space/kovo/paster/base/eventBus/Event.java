package space.kovo.paster.base.eventBus;

public abstract class Event<DATA_TYPE> {

    protected DATA_TYPE data;

    public Event(DATA_TYPE data) {
        this.data = data;
    }

    public DATA_TYPE getData() {
        return this.data;
    }

    public void setData(DATA_TYPE data) {
        this.data = data;
    }
}
