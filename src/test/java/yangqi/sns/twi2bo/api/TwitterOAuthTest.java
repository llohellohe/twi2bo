package yangqi.sns.twi2bo.api;

import junit.framework.TestCase;
import org.junit.Test;
import yangqi.oauth1.OAuthToken;

import java.io.IOException;

public class TwitterOAuthTest extends TestCase {

    @Test
    public void testGetRequestToken() throws IOException {
        TwitterOAuth twitterOAuth=new TwitterOAuth(APIConstants.CONSUMER_KEY,APIConstants.CONSUMER_SECRET);
        OAuthToken requestToken=twitterOAuth.requestToken(APIConstants.CALLBACK_URL);
        System.out.println("request token is " + requestToken);
        assertNotNull(requestToken);
        assertNotNull(requestToken.getToken());
        assertNotNull(requestToken.getTokenSecret());

        twitterOAuth.authenticate(requestToken.getToken());
    }

    @Test
    public void testGetAccessToken() throws IOException {
        TwitterOAuth twitterOAuth=new TwitterOAuth(APIConstants.CONSUMER_KEY,APIConstants.CONSUMER_SECRET);
        OAuthToken accessToken=twitterOAuth.accessToken("ZBWF33aYTmszQ54Z97z69xbRHAJ957tr","luJUeuxGyx5vNbznSfAgQpfdmrl2mJtg");
        System.out.println("access token is " + accessToken);
        assertNotNull(accessToken);
        assertNotNull(accessToken.getToken());
        assertNotNull(accessToken.getTokenSecret());
    }
}
