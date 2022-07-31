package space.kovo.paster.dtos.loginDto;

import space.kovo.paster.base.dtos.ResponseDTO;

public class LoginResponseDTO extends ResponseDTO {

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
