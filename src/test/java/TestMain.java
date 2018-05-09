import com.tiza.support.model.RespResult;
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

    @Test
    public void test1(){
        RespResult result = new RespResult();
        result.setRet(0);
        result.setMsg("");
        result.setData("uxeKP0ezR5yL8xSg4/ZCDh/N91/u86NXFxd2DrwZVW8zCPYcpl59Twz/yQZ3RaO4rDDrGmkvQignmNEJ+k4PGxdmIC+4fpJ8rU6osSobY+AeA0uueuQ5+eQiWBL6p6v5XMMm91brtK8yfFELYUWQzVcxABnAwK/+dyxtUhqLIxUpkwTEU/4ktN40df9IzzlLO5uvUknPGYu9yL0pp5w9vdRxmA1RiiTDNCysz6klr9bunGV3VJa2qpLcgeZMf/oG");

        result.buildSig("1234567890abcdef");

        System.out.println(result.getSig());
    }
}
