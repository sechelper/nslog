package icu.nslog.dns;

import icu.nslog.exception.NslogException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @className: AESEncoder
 * @description: TODO
 * @author: cookun
 * @date: 1/14/22
 **/
public class AESEncoder implements RecordEncoder {

    private String key = "4%YkW!@g5LGcf9Ut";

    private static final String AES_PKCS5P = "AES/ECB/PKCS5Padding";

    private static final String ALGORITHM = "AES";

    private static Cipher cipher;

    public AESEncoder(){
        if (StringUtils.isEmpty(key)) {
            // TODO 密钥强度检查
            throw new RecordEncoderException("Weak Password");
        }
    }

    public AESEncoder(String key) {
        this.key = key;

    }

    @Override
    public String decrypt(String s) {
        try {
            cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM));
            return new String(cipher.doFinal(Base64.decodeBase64(s)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
            throw new RecordEncoderException(e.getMessage());
        }
    }

    @Override
    public String encryption(String s) {
        try {
            cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM));
            return Base64.encodeBase64String(cipher.doFinal(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
            throw new RecordEncoderException(e.getMessage());
        }

    }

    public static void main(String[] args) {
        AESEncoder encoder = new AESEncoder();

        System.out.println(encoder.decrypt("Gk+ZsHsUZlIGgD0+0Hjx6Q=="));
        System.out.println(encoder.encryption("test"));
    }


}
