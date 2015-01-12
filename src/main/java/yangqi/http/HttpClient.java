package yangqi.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import yangqi.sns.twi2bo.api.APIConstants;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangqi on 1/12/15.
 */
public class HttpClient {

    private DefaultHttpClient httpclient = new DefaultHttpClient();

    public HttpClient() {
    }

    public String get(String apiUrl) throws IOException {
        HttpGet httpGet = new HttpGet(apiUrl);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = convert2String(entity.getContent());
        return result;
    }

    private String convert2String(InputStream is) throws IOException {
        return IOUtils.toString(is, APIConstants.ENCODING);
    }
}
