package yangqi.oauth1;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OAuthUtilTest {

    @Test
    public void testPercentString() throws Exception {

    }

    @Test
    public void testBuildHttpQuery() throws Exception {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("status", "Hello Ladies + Gentlemen, a signed OAuth request!");
        queryMap.put("include_entities", "true");
        queryMap.put("oauth_consumer_key", "xvz1evFS4wEEPTGEFPHBog");
        queryMap.put("oauth_nonce", "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
        queryMap.put("oauth_signature_method", "HMAC-SHA1");
        queryMap.put("oauth_timestamp", "1318622958");
        queryMap.put("oauth_token", "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb");
        queryMap.put("oauth_version", "1.0");

        String httpQuery = OAuthUtil.buildHttpQuery(queryMap);
        assertEquals("include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21",
                     httpQuery);
    }
}
