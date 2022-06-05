package space.kovo.paster.dtos.loginDto;

import space.kovo.paster.dtos.ErrorResponseDTO;

import java.util.List;

public class LoginErrorResponseDTO extends ErrorResponseDTO {
    private List<ErrorResponseDTO> messages;

    public List<ErrorResponseDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ErrorResponseDTO> messages) {
        this.messages = messages;
    }
}
