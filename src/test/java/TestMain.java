import com.tiza.support.model.RespResult;
import com.tiza.support.util.AESUtil;
import com.tiza.support.util.HMacMD5;
import com.tiza.support.util.JacksonUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2018-05-03 14:59
 */
public class TestMain {


    @Test
    public void test() throws Exception{
        String dataSecret = "1234567890abcdef";
        String dataSecretIv = "1234567890abcdef";
        String str = "{\"OperatorSecret\":\"1234567890abcdef\"}";

        String s = AESUtil.Encrypt(str, dataSecret, dataSecretIv);
        System.out.println(s);

        str = "0hRB5aTED8zKH9U+mZtzE9rhQ53h4LJP3q6VY4WGf5u/03lYhYK/rtF7kkZYm/chQkL9IN54mDFzA3qUJvWMnIEzfSCdzpebMYlk3ycl46MNaFd8pHvSdYrtn9OBanwY1F24AB+uY2WQf4VPq9q1iXsQUpZPIJoU1BcwiLPSU61oIMBHMsdQJXsbWsQE0gLa";
        System.out.println(AESUtil.Decrypt(str, dataSecret, dataSecretIv));
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

    @Test
    public void test2(){
        String sigSecret="1234567890abcdef";
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("395815801")
                .append("")
                .append("20181204102936")
                .append("0001");

        String str = HMacMD5.getHmacMd5Str(sigSecret, strBuf.toString());
        System.out.println(str);

    }
}
