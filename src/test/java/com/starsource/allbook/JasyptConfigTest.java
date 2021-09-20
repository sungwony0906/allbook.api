package com.starsource.allbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    private String key = "samplePassword";

    @Test
    void jasypt() {
        String apple = "APPLE";

        String encryptApple = jasyptEncrypt(apple);

        System.out.println(encryptApple);
        assertThat(apple).isEqualTo(jasyptDecrypt(encryptApple));
    }

    private String jasyptEncrypt(String input) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecrypt(String input) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
