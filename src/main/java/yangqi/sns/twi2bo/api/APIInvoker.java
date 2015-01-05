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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

    public void singInWithTwitter() throws IOException {

        HttpPost httpPost = new HttpPost(APIConstants.API_SERVER + "/oauth/request_token");

        String callbackUrl = URLEncoder.encode(APIConstants.CALLBACK_URL, APIConstants.ENCODING);

        httpPost.addHeader("Authorization", "OAuth oauth_callback=" + callbackUrl + "\noauth_consumer_key="
                                            + APIConstants.CONSUMER_KEY
                                            + "\noauth_nonce=ea9ec8429b68d6b77cd5600adbbb0456"
                                            + "\noauth_signature=ea9ec8429b68d6b77cd5600adbbb0456"
                                            + "\noauth_signature_method=HMAC-SHA1"
                                            + "\noauth_timestamp="+System.currentTimeMillis()
                                            + "\noauth_version=1.0"
        );

       // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //nvps.add(new BasicNameValuePair("oauth_callback", callbackUrl));
        //httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = convert2String(entity.getContent());

        System.out.println(System.currentTimeMillis()/1000+"result is "+result);

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
