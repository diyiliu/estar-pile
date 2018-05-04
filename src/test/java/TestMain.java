import com.tiza.support.util.AESUtil;
import org.junit.Test;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2018-05-03 14:59
 */
public class TestMain {


    @Test
    public void test() throws Exception{
        String dataSecret = "2BMSzYtvC4pubJLy";
        String dataSecretIv = "94bxWrkn4S0TB8FO";
        String str = "{" +
                "  \"ConnectorStatusInfo\": " +
                "{" +
                "\"ConnectorID\":\"3702110116101\"," +
                "\"Status\":1" +
                "}" +
                "}";

        String s = AESUtil.Encrypt(str, dataSecret, dataSecretIv);
        System.out.println(s);


        System.out.println(AESUtil.Decrypt(s, dataSecret, dataSecretIv));
    }
}
