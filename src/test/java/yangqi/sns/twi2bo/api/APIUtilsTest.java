package yangqi.sns.twi2bo.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class APIUtilsTest {

    @Test
    public void testPercent() {
        assertEquals("Ladies%20%2B%20Gentlemen", APIUtils.percentString("Ladies + Gentlemen"));
        assertEquals("An%20encoded%20string%21", APIUtils.percentString("An encoded string!"));
    }


}
