package com.honvay.cola.auth.base.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 */
public class Utils {

    private static PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmmLp/WQpWmRSqYdfLM6GbsMR67sUfX9vk+s37h2upcF1VfLX96WVCt5X4oQteXCd4MFp1WXGWQzJllyR1/rMQPLuFSldimzBjiYlLUzM9CmXBqKjnz31HOCymFhKZt6lHAlpbDCNR94xbF6N/Mttwrpd+iFXhGvuQLiwR4KYNO3/JuPDK+P/WPrp7dWQIurkwbtiOqwbcbhvca8yuscDio7n14+e2KZt/UkjFNNkH0Eyk6DJ57Tlxsbzrkgxeyiy3tOsOEwo9sa6XzcBmOiOMXclOvSW8Hk5d/73qC25FRHcEBGuaOEiOosV1vFlvgPDGN9s+kPtpUuhw8uqWIvY5AgMBAAECggEAAdKX7768ozfnhvmtD7cP5JEphSLJm09EK5wm1sKjheYJz16e6xk7l2BEzsSMSEr/z4vP4YrJ+Ffh3ESsDHJt6RaRAqNOk3CZNe2SR/WwENHlTRoYRvj5KOdPbb2+FFpzCqjW7joSxNnNek2m50RaLEBsveCl9+GAsRIgP03S40An11ae7CQyrp6LoTlk72MDLcdB5q0mKpydm7x0zUXuMxKnEERiQxmMtLM6NFbVBgF2JIRSkWwY1e3tOWFLo53mgHSJofT+QBQQ7679qeMlLQAFBehPYLetqIpXwwzvspCh72bM/dYi9SEZCCjeRVXVjlyiS38OK1mK7+5vHNcxUQKBgQDpSEB9wPmrB1WJ1tC7raEYuphFzKVqO8cnHha7hulv0oWUlbBaXfwCL4B4igPjOXnYL2iJiWWgSEqVAQ5XbY0CXCXfEJBRFfQAGP4huoxXJ1+tYdm6KvOtcf9CsyhxY3DBpeJHqCGRcClFpa2hBsQAdWNGePnVIvL31twKKDv+RQKBgQC20f9Rj9h6AYeg1kN8KSH+b/g/8o/X+2tAT6A2Bwezmw91YdOs8thVvdAP8I5gQ0ZVws+bdopcz9616OaZ1qAGK1gH4t2x/SPKbyCLbwy/2JrXwZEyyY8uO59B8nEuVQi6tg9vDeRyHWI74fIA0PUIhlGrOBOFGo+uYS/anlnhZQKBgQC1JxJNlSlMEqnuHA6Db1WGKoBM05owYLNKQSdOAHoOB+ELfSIxSoKD//c3o5VZQizIICiBhVyRlxGzm9pTMhMqHfyat/YZwX51BPNzpbWNiFnX/gBhfTE1Iy0h0pY5VwsclYCePIW0tvCV0Q5/2Q0jfgTVmOQJCta6dvLwQEY2MQKBgFgO+GsB3WSO/hQjgGf6rAwRRMMQg+aljHhyohnD8xq40o5Yq4u/gSJzhhdXaEzHusYuSq94PpcL7Rsz7nclIPk3wXQy+1PzOV0Vf9iJiduSaqsH6ndLDLNZNpGeRJPrk4PTk8WLLIEm1B1B7L4l6BDOGeJd5VEUhSaaW2FzKpndAoGAdByWt94Zmlv7ZAcmFlLsm8Par3WFaeWszQK1xBpyj3I5h2y8qLd9nfAI89xfWNcYbetNmkjnQO5ymGQuheTppplJuh3Qxc1Kb1bGIoUDSjumivbc7wOikMKi6AdgPhQd5H21gWz1qhoagcuPygR8P1VswNwPyiy2Ua6YBPBGE9Q=";
        PKCS8EncodedKeySpec priPKCS8;
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static String passwordEncoder(String password){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        return encodedPassword;
    }

	public static void main(String[] args) {
		System.out.println(Utils.passwordEncoder("123"));
	}
}
