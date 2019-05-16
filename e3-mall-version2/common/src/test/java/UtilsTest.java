import com.hmx.e3_common.utils.FastDFSClient;
import org.junit.Test;

public class UtilsTest {
    @Test
    public void uploadTest() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("E:/eclipse/e3-mall-version2/e3mallweb/src/main/resources/upload.conf");
        String jpg = fastDFSClient.uploadFile("D:/img/3dd07e14f32c4cf3ae1b4a13bc63a9ad.jpg", "jpg", null);
        System.out.println(jpg);
    }
}
