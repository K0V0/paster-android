package space.kovo.paster.services.jwtService;

public class JwtServiceImpl implements JwtService {

    private static final String PREFIX = "Bearer";

    @Override
    public String prefixizeToken(String jwtToken) {
        return String.format("%s %s", PREFIX, jwtToken);
    }
}
