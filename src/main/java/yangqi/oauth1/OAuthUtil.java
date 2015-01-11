package yangqi.oauth1;

import net.oauth.OAuth;

import java.util.*;

/**
 * Created by yangqi on 1/5/15.
 */
public class OAuthUtil {

    public static String percentString(String query) {
        return OAuth.percentEncode(query);
    }

    public static String buildHttpQuery(Map<String,String> parameterMap){
        if (parameterMap == null || parameterMap.size() == 0) {
            return "";
        }

        int keySize = parameterMap.size();
        Map<String, String> percentMap = new HashMap<>(keySize);
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            percentMap.put(OAuthUtil.percentString(entry.getKey()), OAuthUtil.percentString(entry.getValue()));
        }

        List<String> keyList = new ArrayList<>(keySize);
        for (String key : percentMap.keySet()) {
            String percentKey = OAuthUtil.percentString(key);
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
}
