package yangqi.sns.twi2bo.api;

import yangqi.http.HttpClient;
import yangqi.oauth1.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqi on 1/11/15.
 */
public class TwitterOAuth {
    private OAuthConsumer consumer;
    private OAuthToken    token;
    private OAuthSignatureMethod signatureMethod = new HmacSHA1SignatureMethod();
    private OAuthRequest         request         = null;
    private HttpClient           client          = new HttpClient();


    public TwitterOAuth(String consumerKey, String consumerSecret) {
        consumer = new OAuthConsumer(consumerKey, consumerSecret);
    }

    public TwitterOAuth(String consumerKey, String consumerSecret,String tokenKey,String tokenSecret) {
        consumer = new OAuthConsumer(consumerKey, consumerSecret);
        token=new OAuthToken(tokenKey,tokenSecret);
    }

    public void buildRequest(String httpUrl,String httpMethod,Map<String,String> parameters){
        request=new OAuthRequest(httpUrl,httpMethod);
        parameters.put("oauth_consumer_key",consumer.getKey());
        parameters.put("oauth_nonce",getNonce());
        parameters.put("oauth_signature_method",signatureMethod.getName());
        parameters.put("oauth_timestamp",getTimestamp());
        if(token!=null) {
            parameters.put("oauth_token", token.getToken());
        }
        parameters.put("oauth_version","1.0");


        request.setParameters(parameters);

        String signature=signatureMethod.signature(request,consumer,token);
        parameters.put("oauth_signature",signature);

        System.out.println("signature is "+signature);

        System.out.println("parameters is "+request.getParameters());

    }

    public OAuthToken requestToken(String callbackUrl){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("oauth_callback",callbackUrl);
        buildRequest("https://api.twitter.com/oauth/request_token", "get", queryMap);
        String getQuery=request.buildGetQuery();

        try {
            String result=client.get("https://api.twitter.com/oauth/request_token?"+getQuery);

            if (result != null) {
                String[]resultArray=result.split("&");
                Map<String,String> retMap=new HashMap<>();
                for(String resultEntry:resultArray){
                   String[]valueArray=resultEntry.split("=");
                    retMap.put(valueArray[0],valueArray[1]);
                }

                OAuthToken requestToken=new OAuthToken(retMap.get("oauth_token"),retMap.get("oauth_token_secret"));
                return requestToken;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void authenticate(String token){
        System.out.println("https://api.twitter.com/oauth/authenticate?oauth_token="+token);
        System.out.println("=======GOT oauth_verifier YOURSELF=======");
    }

    public OAuthToken accessToken(String token,String oauthVerifier){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("oauth_token",token);
        queryMap.put("oauth_verifier",oauthVerifier);
        buildRequest("https://api.twitter.com/oauth/access_token", "get", queryMap);
        String getQuery=request.buildGetQuery();

        try {
            String result=client.get("https://api.twitter.com/oauth/access_token?"+getQuery);

            if (result != null) {
                System.out.println(result);
                String[]resultArray=result.split("&");
                Map<String,String> retMap=new HashMap<>();
                for(String resultEntry:resultArray){
                    String[]valueArray=resultEntry.split("=");
                    retMap.put(valueArray[0],valueArray[1]);
                }

                OAuthToken requestToken=new OAuthToken(retMap.get("oauth_token"),retMap.get("oauth_token_secret"));
                return requestToken;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OAuthConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(OAuthConsumer consumer) {
        this.consumer = consumer;
    }

    public OAuthToken getToken() {
        return token;
    }

    public void setToken(OAuthToken token) {
        this.token = token;
    }

    public OAuthSignatureMethod getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(OAuthSignatureMethod signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public OAuthRequest getRequest() {
        return request;
    }

    public void setRequest(OAuthRequest request) {
        this.request = request;
    }
    private String getTimestamp() {
        return ""+System.currentTimeMillis()/1000;
    }

    /**
     * TODO 增加 nonce的实现
     * @return
     */
    private String getNonce() {
        return "1eb7305f00ddc704e303a3a279ade8b8";
    }
}
