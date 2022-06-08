package space.kovo.paster.activities.loginActivity;

public final class LoginRequestSerializationException extends RuntimeException {
    public LoginRequestSerializationException() { super("error during json request serialization for login"); }
}
