package yangqi.oauth1;

/**
 * Created by yangqi on 1/11/15.
 */
public interface OAuthSignatureMethod {
    String getName();

    String signature(OAuthRequest request,OAuthConsumer consumer,OAuthToken token);
}
