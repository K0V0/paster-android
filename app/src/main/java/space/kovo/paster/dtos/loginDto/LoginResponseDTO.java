package space.kovo.paster.dtos.loginDto;

import space.kovo.paster.dtos.ResponseDTOimpl;

public class LoginResponseDTO extends ResponseDTOimpl {

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
