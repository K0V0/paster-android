package space.kovo.paster.base.dtos;

public abstract class ResponseDTO {
    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
