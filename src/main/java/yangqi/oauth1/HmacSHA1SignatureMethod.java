package yangqi.oauth1;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by yangqi on 1/11/15.
 */
public class HmacSHA1SignatureMethod implements OAuthSignatureMethod{

    @Override
    public String getName() {
        return "HMAC-SHA1";
    }

    @Override
    public String signature(OAuthRequest request, OAuthConsumer consumer, OAuthToken token) {
        if(request==null||request.getSignatureBaseString()==null||request.getSignatureBaseString().equals("")||consumer==null){
            return "";
        }
        String baseString=request.getSignatureBaseString();
        String signKey=createSigningKey(consumer,token);

        System.out.println("===SIgn KEY IS===="+signKey);

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(signKey.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(baseString.getBytes());
            byte[] result= Base64.getEncoder().encode(digest);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createSigningKey(OAuthConsumer consumer,OAuthToken token) {
        if(consumer==null){
           return null;
        }
        StringBuffer signingKey = new StringBuffer();
        signingKey.append(OAuthUtil.percentString(consumer.getSecret())).append("&");
        if(token!=null&&token.getTokenSecret()!=null){
           signingKey.append(OAuthUtil.percentString(token.getTokenSecret()));
        }
        return signingKey.toString();
    }

}
