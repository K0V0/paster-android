package space.kovo.paster.services.httpService;

public final class ErrorResponseParsingException extends RuntimeException {
    public ErrorResponseParsingException() {
        super("JSON error response handling malfunction");
    }
}
