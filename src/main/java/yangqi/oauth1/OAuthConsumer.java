package yangqi.oauth1;

/**
 * Created by yangqi on 1/11/15.
 */
public class OAuthConsumer {
    private String key;
    private String secret;

    public OAuthConsumer(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
