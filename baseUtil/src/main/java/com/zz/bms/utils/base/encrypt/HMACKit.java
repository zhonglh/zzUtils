package com.zz.bms.utils.base.encrypt;



import com.zz.bms.utils.base.data.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HMACKit {

    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static String key                 = "*&^%$6jdkhf1234sdfa21saw700%$32430-{}HMD90AA:?><>?";

    private HMACKit() {}

    /**
     * @Title: calculateRFC2104HMAC
     * @Description: HMAC加密
     * @param data
     * @return
     */
    private static String calculateRFC2104HMAC(String data){
        String result = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes ("UTF-8"),HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance (HMAC_SHA1_ALGORITHM);
            mac.init (signingKey);
            byte[] rawHmac = mac.doFinal (data.getBytes ("UTF-8"));
            result = Base64.encodeBytes(rawHmac);
            result = result.replace ('+', '-');
            result = result.replace ('/', '_');
        } catch (Exception e) {}
        return result;
    }

    /** 加密 */
    public static String sign(String data){
        return calculateRFC2104HMAC (data);
    }

    public static void main(String[] args){
        System.out.println (sign ("123456"));
    }
}
