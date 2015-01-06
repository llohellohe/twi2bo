package yangqi.sns.twi2bo.api.sign;

import org.junit.Test;
import yangqi.sns.twi2bo.api.APIParameter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SignatureToolTest {

    @Test
    public void testCollectParameter() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("status", "Hello Ladies + Gentlemen, a signed OAuth request!");
        queryMap.put("include_entities", "true");
        queryMap.put("oauth_consumer_key", "xvz1evFS4wEEPTGEFPHBog");
        queryMap.put("oauth_nonce", "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
        queryMap.put("oauth_signature_method", "HMAC-SHA1");
        queryMap.put("oauth_timestamp", "1318622958");
        queryMap.put("oauth_token", "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb");
        queryMap.put("oauth_version", "1.0");

        String signature = SignatureTool.collectParameter(queryMap);
        System.out.println(signature);
        assertEquals("include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21",
                     signature);
    }

    @Test
    public void testCreateSignatureBaseString() {
        String signatureBase = SignatureTool.createSigatureBaseString("POST",
                                                                 "https://api.twitter.com/1/statuses/update.json",
                                                                 "include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21");

        assertEquals(signatureBase,
                     "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521");
    }

    @Test
    public void testCreateSigningKey() {
        String signingKey = SignatureTool.createSigningKey("kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw",
                                                      "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE");
        assertEquals(signingKey,
                     "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw&LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE");
    }

    @Test
    public void testSign(){
        String sign=SignatureTool.caculateSignature("kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw&LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE", "POST&https%3A%2F%2Fapi.twitter.com%2F1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521");
        assertEquals(sign,"tnnArxj06cWHq44gCs1OSKk/jLY=");
    }

    @Test
    public void testCreateOAuthHeader(){

        APIParameter parameter=new APIParameter();
        parameter.setoAuthConsumerKey("xvz1evFS4wEEPTGEFPHBog");
        parameter.setoAuthNonce("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
        parameter.setoAuthSignature("tnnArxj06cWHq44gCs1OSKk/jLY=");
        parameter.setTimestamp("" + 1318622958);
        parameter.setoAuthToken("370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb");
        parameter.setoAuthVersion("1.0");

        String oauthHeader=SignatureTool.createOAuthHeader(parameter);

        assertEquals(oauthHeader,"OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"tnnArxj06cWHq44gCs1OSKk%2FjLY%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1318622958\", oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", oauth_version=\"1.0\"");
    }

}
