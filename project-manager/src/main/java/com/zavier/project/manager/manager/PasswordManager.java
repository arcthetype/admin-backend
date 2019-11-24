package com.zavier.project.manager.manager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordManager {

    public String encryptPassword(String password) {
        byte[] digest = DigestUtils.getSha512Digest().digest(password.getBytes());
        return Base64.encodeBase64URLSafeString(digest);
    }
}
