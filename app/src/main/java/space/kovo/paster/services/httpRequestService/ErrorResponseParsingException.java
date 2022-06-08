package space.kovo.paster.services.httpRequestService;

public final class ErrorResponseParsingException extends RuntimeException {
    public ErrorResponseParsingException() {
        super("JSON error response handling malfunction");
    }
}
