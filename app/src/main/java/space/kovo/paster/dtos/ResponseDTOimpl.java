package space.kovo.paster.dtos;

public class ResponseDTOimpl implements ResponseDTO {
    protected String status;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
