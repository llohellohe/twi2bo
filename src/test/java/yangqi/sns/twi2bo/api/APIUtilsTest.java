package yangqi.sns.twi2bo.api;

import org.junit.Test;
import yangqi.oauth1.OAuthUtil;

import static org.junit.Assert.assertEquals;

public class APIUtilsTest {

    @Test
    public void testPercent() {
        assertEquals("Ladies%20%2B%20Gentlemen", OAuthUtil.percentString("Ladies + Gentlemen"));
        assertEquals("An%20encoded%20string%21", OAuthUtil.percentString("An encoded string!"));
    }


}
