import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;


public class FDSTest {
    @Test
    public void ftsTest() throws IOException, MyException {
        //加载配置文件
        ClientGlobal.init("E:/eclipse/e3-mall-version2/e3mallweb/src/main/resources/upload.conf");
        //创建TrackerClient对象
        TrackerClient tc = new TrackerClient();
        //根据TrackerClient获取Server对象
        TrackerServer trackerServer = tc.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        String[] jpgs = storageClient.upload_file("D:/img/1.jpg", "jpg", null);
        for (String str:jpgs) {
            System.out.println(str);
        }


    }
}
