package com.example.spring.service;

import org.springframework.stereotype.Service;
import org.jasypt.util.text.BasicTextEncryptor;

@Service
public class EncryptionService {

    private BasicTextEncryptor textEncryptor;

    public EncryptionService() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretKey");  // 암호화 키 설정
    }

    // 암호화 메서드
    public String encrypt(String plainText) {
        return textEncryptor.encrypt(plainText);
    }

    // 복호화 메서드
    public String decrypt(String encryptedText) {
        return textEncryptor.decrypt(encryptedText);
    }
}
