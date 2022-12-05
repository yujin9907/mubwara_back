package site.metacoding.finals.config.jwt;

public interface JwtSecret {
    String SECRET = "SAMPLE_SECRET_MSG";
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
}
