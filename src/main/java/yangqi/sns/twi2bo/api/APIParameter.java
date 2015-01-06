package yangqi.sns.twi2bo.api;

/**
 * Created by yangqi on 1/6/15.
 */
public class APIParameter {
    private String oAuthConsumerKey;
    private String oAuthConsumerSecret;
    private String oAuthNonce;
    private String oAuthSignature;
    private String oAuthSignatureKey;
    private String oAuthSignatureMethod="HMAC-SHA1";
    private String timestamp;
    private String oAuthToken;
    private String oAuthVersion="1.0";

    private String oAuthTokenSecret;

    private String apiName;

    public String getoAuthConsumerKey() {
        return oAuthConsumerKey;
    }

    public void setoAuthConsumerKey(String oAuthConsumerKey) {
        this.oAuthConsumerKey = oAuthConsumerKey;
    }

    public String getoAuthNonce() {
        return oAuthNonce;
    }

    public void setoAuthNonce(String oAuthNonce) {
        this.oAuthNonce = oAuthNonce;
    }

    public String getoAuthSignature() {
        return oAuthSignature;
    }

    public void setoAuthSignature(String oAuthSignature) {
        this.oAuthSignature = oAuthSignature;
    }

    public String getoAuthSignatureKey() {
        return oAuthSignatureKey;
    }

    public void setoAuthSignatureKey(String oAuthSignatureKey) {
        this.oAuthSignatureKey = oAuthSignatureKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getoAuthToken() {
        return oAuthToken;
    }

    public void setoAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String getoAuthVersion() {
        return oAuthVersion;
    }

    public void setoAuthVersion(String oAuthVersion) {
        this.oAuthVersion = oAuthVersion;
    }

    public String getoAuthSignatureMethod() {
        return oAuthSignatureMethod;
    }

    public void setoAuthSignatureMethod(String oAuthSignatureMethod) {
        this.oAuthSignatureMethod = oAuthSignatureMethod;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getoAuthTokenSecret() {
        return oAuthTokenSecret;
    }

    public void setoAuthTokenSecret(String oAuthTokenSecret) {
        this.oAuthTokenSecret = oAuthTokenSecret;
    }

    public String getoAuthConsumerSecret() {
        return oAuthConsumerSecret;
    }

    public void setoAuthConsumerSecret(String oAuthConsumerSecret) {
        this.oAuthConsumerSecret = oAuthConsumerSecret;
    }
}
