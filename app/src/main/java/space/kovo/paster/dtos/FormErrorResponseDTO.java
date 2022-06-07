package space.kovo.paster.dtos;

public class FormErrorResponseDTO extends ResponseDTO {
    protected String code;
    protected String message;

    public FormErrorResponseDTO() {}

    public FormErrorResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
