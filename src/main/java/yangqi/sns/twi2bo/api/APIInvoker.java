package yangqi.sns.twi2bo.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by yangqi on 1/3/15.
 */
public class APIInvoker {

    private DefaultHttpClient httpclient = new DefaultHttpClient();

    public String invoke(String apiName) throws IOException {
        HttpGet httpget = new HttpGet(APIConstants.API_SERVER + apiName);

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        String result = convert2String(entity.getContent());

        return result;
    }

    public void close() {
        httpclient.getConnectionManager().shutdown();
    }

    public void updateStatus(String tweets) throws IOException{

        TwitterOAuth twitterOAuth=new TwitterOAuth(APIConstants.CONSUMER_KEY,APIConstants.CONSUMER_SECRET,APIConstants.ACCESS_TOKEN,APIConstants.ACCESS_TOKEN_SECRET);

        Map<String,String> parameters=new HashMap<>();
        parameters.put("status",tweets);

        twitterOAuth.buildRequest("https://api.twitter.com/1.1/statuses/update.json","post",parameters);

        String oauthHeader=twitterOAuth.getRequest().buildAuthorizationHeader();
        System.out.println("HEADER is "+oauthHeader);

        HttpPost httpPost = new HttpPost(APIConstants.API_SERVER + "/1.1/statuses/update.json");
        httpPost.addHeader("Authorization",oauthHeader);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("status", tweets));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = convert2String(entity.getContent());


        System.out.println("result is "+result);


    }

    public void singInWithTwitter() throws IOException {


        TwitterOAuth twitterOAuth=new TwitterOAuth(APIConstants.CONSUMER_KEY,APIConstants.CONSUMER_SECRET);


        Map<String, String> queryMap = new HashMap<>();
        //queryMap.put("oauth_callback",APIConstants.CALLBACK_URL);
        queryMap.put("oauth_callback",APIConstants.CALLBACK_URL);

        twitterOAuth.buildRequest("https://api.twitter.com/oauth/request_token", "get", queryMap);


        String getQuery=twitterOAuth.getRequest().buildGetQuery();
        System.out.println(getQuery);

        HttpGet httpGet = new HttpGet(APIConstants.API_SERVER + "/oauth/request_token?"+getQuery);


        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = convert2String(entity.getContent());

        System.out.println("result is "+result);

    }

    private String getBearTokenJson(String apiName) throws IOException {
        HttpPost httpPost = new HttpPost(APIConstants.API_SERVER + apiName);

        String bearerToken = encodeKeyAndSecert();

        httpPost.addHeader("Authorization", "Basic " + bearerToken);
        // httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        String result = convert2String(entity.getContent());
        return result;
    }

    public String getBearerToken() throws IOException {
        String resultJson = getBearTokenJson("/oauth2/token");
        if (resultJson != null) {
            Map<String, String> retMap = (Map<String, String>) JSONObject.parse(resultJson);
            if (retMap != null) {
                String tokenType = retMap.get("token_type");
                String bearerToken = retMap.get("access_token");

                if ("bearer".equals(tokenType)) {
                    return bearerToken;
                }
            }
        }
        return null;
    }

    public String getUserTimeLine() throws IOException {
        String bearerToken = getBearerToken();
        HttpGet httpGet = new HttpGet(APIConstants.API_SERVER
                                      + "/1.1/statuses/user_timeline.json?count=100&screen_name=twitterapi");
        httpGet.addHeader("Authorization", "Bearer " + bearerToken);

        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String result = convert2String(entity.getContent());
        return result;

    }

    private String convert2String(InputStream is) throws IOException {
        return IOUtils.toString(is, APIConstants.ENCODING);
    }

    public String encodeKeyAndSecert() {
        try {
            String encodeKey = URLEncoder.encode(APIConstants.CONSUMER_KEY, APIConstants.ENCODING);
            String encodeSecret = URLEncoder.encode(APIConstants.CONSUMER_SECRET, APIConstants.ENCODING);

            String bearerToken = encodeKey + ":" + encodeSecret;
            byte[] base64Result = Base64.getEncoder().encode(bearerToken.getBytes());
            return new String(base64Result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
