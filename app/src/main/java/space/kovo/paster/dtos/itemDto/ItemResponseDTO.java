package space.kovo.paster.dtos.itemDto;

import space.kovo.paster.base.dtos.ResponseDTO;

import java.util.Objects;

public class ItemResponseDTO extends ResponseDTO {
    private long id;
    private String text;
    private String preview;
    private long timestamp;
    private boolean isLarge;
    private String platform;
    private String deviceName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLarge() {
        return isLarge;
    }

    public void setLarge(boolean large) {
        isLarge = large;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp);
    }
}
