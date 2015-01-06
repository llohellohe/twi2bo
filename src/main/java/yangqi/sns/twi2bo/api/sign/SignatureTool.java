package yangqi.sns.twi2bo.api.sign;

import yangqi.sns.twi2bo.api.APIConstants;
import yangqi.sns.twi2bo.api.APIParameter;
import yangqi.sns.twi2bo.api.APIUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * https://dev.twitter.com/oauth/overview/creating-signatures Created by yangqi on 1/5/15.
 */
public class SignatureTool {


    public static String collectParameter(Map<String, String> parameterMap) {
        if (parameterMap == null || parameterMap.size() == 0) {
            return null;
        }

        int keySize = parameterMap.size();
        Map<String, String> percentMap = new HashMap<>(keySize);
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            percentMap.put(APIUtils.percentString(entry.getKey()), APIUtils.percentString(entry.getValue()));
        }

        List<String> keyList = new ArrayList<>(keySize);
        for (String key : percentMap.keySet()) {
            String percentKey = APIUtils.percentString(key);
            keyList.add(percentKey);
        }
        Collections.sort(keyList);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < keySize; i++) {
            String ele = keyList.get(i);
            buffer.append(ele).append("=").append(percentMap.get(ele));

            if (i < keySize - 1) {
                buffer.append("&");
            }
        }

        return buffer.toString();
    }

    public static String createSigatureBaseString(String httpMethod, String url, String parameterString) {
        StringBuffer signature = new StringBuffer();
        signature.append(httpMethod.toUpperCase()).append("&").append(APIUtils.percentString(url)).append("&").append(APIUtils.percentString(parameterString));
        return signature.toString();

    }

    public static String createSigningKey(String consumerSecret, String tokenSecret) {
        StringBuffer signingKey = new StringBuffer();

        signingKey.append(APIUtils.percentString(consumerSecret)).append("&").append(APIUtils.percentString(tokenSecret));
        return signingKey.toString();
    }


    public static String caculateSignature(String signKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(signKey.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(data.getBytes());
            byte[] result=Base64.getEncoder().encode(digest);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createOAuthHeader(APIParameter parameter){
        StringBuffer oauthHeader=new StringBuffer();
        oauthHeader.append("OAuth ");
        oauthHeader.append(percentString("oauth_consumer_key")).append("=\"").append(percentString(parameter.getoAuthConsumerKey())).append("\", ");
        oauthHeader.append(percentString("oauth_nonce")).append("=\"").append(percentString(parameter.getoAuthNonce())).append("\", ");
        oauthHeader.append(percentString("oauth_signature")).append("=\"").append(percentString(parameter.getoAuthSignature())).append("\", ");
        oauthHeader.append(percentString("oauth_signature_method")).append("=\"").append(percentString(APIConstants.HMAC_SHA1)).append("\", ");
        oauthHeader.append(percentString("oauth_timestamp")).append("=\"").append(parameter.getTimestamp()).append("\", ");
        oauthHeader.append(percentString("oauth_token")).append("=\"").append(percentString(parameter.getoAuthToken())).append("\", ");
        oauthHeader.append(percentString("oauth_version")).append("=\"").append("1.0\"");
        return oauthHeader.toString();
    }

    private static String percentString(String param){
        return APIUtils.percentString(param);
    }
}
