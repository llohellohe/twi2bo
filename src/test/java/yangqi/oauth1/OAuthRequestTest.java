package yangqi.oauth1;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OAuthRequestTest {
    private OAuthRequest request = null;

    @Before
    public void init(){
        request =new OAuthRequest("https://api.twitter.com/1/statuses/update.json","post");

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("status", "Hello Ladies + Gentlemen, a signed OAuth request!");
        queryMap.put("include_entities", "true");
        queryMap.put("oauth_consumer_key", "xvz1evFS4wEEPTGEFPHBog");
        queryMap.put("oauth_nonce", "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
        queryMap.put("oauth_signature_method", "HMAC-SHA1");
        queryMap.put("oauth_timestamp", "1318622958");
        queryMap.put("oauth_token", "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb");
        queryMap.put("oauth_version", "1.0");

        request.setParameters(queryMap);

    }


    @Test
    public void testGetSignatureBaseString() throws Exception {

        String signatureBase = request.getSignatureBaseString();

        assertEquals(signatureBase,
                     "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521");
    }


}
