package space.kovo.paster.dtos.itemDto;

public class ItemRequestDTO {
    private static final String PLATFORM = "android";
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlatform() {
        return PLATFORM;
    }

}
