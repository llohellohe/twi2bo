package yangqi.oauth1;

/**
 * OAuth 的request token 和 access token
 * Created by yangqi on 1/11/15.
 */
public class OAuthToken {
    private String token;
    private String tokenSecret;

    public OAuthToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
               "token='" + token + '\'' +
               ", tokenSecret='" + tokenSecret + '\'' +
               '}';
    }
}
