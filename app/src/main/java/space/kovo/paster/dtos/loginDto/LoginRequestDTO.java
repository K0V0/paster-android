package space.kovo.paster.dtos.loginDto;

public class LoginRequestDTO {
    private String name;
    private String pass;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
