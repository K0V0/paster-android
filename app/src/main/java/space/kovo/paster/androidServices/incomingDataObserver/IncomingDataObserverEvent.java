package space.kovo.paster.androidServices.incomingDataObserver;

public class IncomingDataObserverEvent {
    private boolean hasNewData;

    public boolean hasNewData() {
        return hasNewData;
    }

    public void setHasNewData(boolean hasNewData) {
        this.hasNewData = hasNewData;
    }
}
