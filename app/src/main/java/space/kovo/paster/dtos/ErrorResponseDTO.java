package space.kovo.paster.dtos;

import java.util.List;
import java.util.Map;

public class ErrorResponseDTO extends ResponseDTO {
    private String code;
    private String message;

    private Map<String, List<ErrorResponseDTO>> errors;

    public ErrorResponseDTO() {}

    public ErrorResponseDTO(FormErrorResponseDTO formErrorResponseDTO) {
        this.code = formErrorResponseDTO.getCode();
        this.message = formErrorResponseDTO.getMessage();
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

    public Map<String, List<ErrorResponseDTO>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<ErrorResponseDTO>> errors) {
        this.errors = errors;
    }
}
