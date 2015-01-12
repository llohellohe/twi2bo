package yangqi.sns.twi2bo.api;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class APIInvokerTest {

    private APIInvoker inovker = new APIInvoker();

    @After
    public void after() {
        inovker.close();
    }

    @Test
    public void testGetBearerToken() throws IOException {
        String result = inovker.getBearerToken();

        assertNotNull(result);
        System.out.println("bearer token is : " + result);
    }

    @Test
    public void testGetUserTimeLine() throws IOException {
        String result = inovker.getUserTimeLine();
        assertNotNull(result);
        System.out.println("result is : " + result);
    }

    @Test
    public void testUpdateStatus() throws IOException {
        inovker.updateStatus("Ignore me !!! I am a test tweets "+System.currentTimeMillis());
    }

}
