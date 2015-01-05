package yangqi.sns.twi2bo.api;

import net.oauth.OAuth;

/**
 * Created by yangqi on 1/5/15.
 */
public class APIUtils {

    public static String percentString(String query) {
        return OAuth.percentEncode(query);
    }


}
