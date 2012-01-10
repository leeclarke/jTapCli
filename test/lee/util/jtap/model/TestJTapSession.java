package lee.util.jtap.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Make sure serialization works.
 * @author leeclarke
 */
public class TestJTapSession {

    private String host = "127.0.0.1";
    private int port = 11210;
    private String password = "password";
    private String bucket = "default";
    
    @Test
    public void testSave() {
        JTapSession session = new JTapSession(host, port, bucket, password);
        session.save();
    }

    @Test
    public void testLoad() {
        JTapSession expected = new JTapSession(host, port, bucket, password);
        expected.save();
        
        JTapSession actual = JTapSession.load();
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getHost(), actual.getHost());
        Assert.assertEquals(expected.getBucket(), actual.getBucket());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getPort(), actual.getPort());
    }
    
    
    @Test
    public void testDelete() {
        JTapSession session = new JTapSession(host, port, bucket, password);
        session.save();
        
        session.delete();
        JTapSession actual = JTapSession.load();
        Assert.assertNull(actual);
    }
}
