package yangqi.oauth1;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HMAC_SHA1_OAuthSignatureMethodTest {

    private OAuthSignatureMethod signatureMethod = new HmacSHA1SignatureMethod();

    @Test
    public void testGetName() throws Exception {
        assertEquals(signatureMethod.getName(), "HMAC-SHA1");
    }

    @Test
    public void testSignature() throws Exception {

        OAuthRequest request = new OAuthRequest("https://api.twitter.com/1/statuses/update.json","post");

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

        OAuthConsumer consumer=new OAuthConsumer("xvz1evFS4wEEPTGEFPHBog","kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw");

        OAuthToken token=new OAuthToken("","LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE");

        String signature=signatureMethod.signature(request,consumer,token);



        assertEquals(signature, "tnnArxj06cWHq44gCs1OSKk/jLY=");
    }

    @Test
    public void testMapCopy() throws Exception {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        map1.put("k2", "v2");
        map1.put("k3", "v3");
        map1.put("k4", "v4");
        System.out.println("map1: " + map1);

        Map<String, String> map2 = new HashMap<>(map1);
        System.out.println("map2: " + map2);

        map2.put("k15", "v15");
        System.out.println("map1: " + map1);
        System.out.println("map2: " + map2);

        map2.remove("k2");
        System.out.println("map1: " + map1);
        System.out.println("map2: " + map2);

        map1.remove("k4");
        System.out.println("map1: " + map1);
        System.out.println("map2: " + map2);

        map1.put("k3", "fuck");
        System.out.println("map1: " + map1);
        System.out.println("map2: " + map2);

    }

    @Test
    public void testTimeStamp(){
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(new Date().getTime());
    }
}
