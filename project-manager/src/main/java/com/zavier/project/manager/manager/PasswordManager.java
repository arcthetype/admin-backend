package com.zavier.project.manager.manager;

import com.zavier.project.common.util.StringUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class PasswordManager {

    public String encryptPassword(String password) {
        DefaultHashService hashService = getDefaultHashService();
        HashRequest request = new HashRequest.Builder()
                .setSource(ByteSource.Util.bytes(password))
                .setSalt(ByteSource.Util.bytes(StringUtil.revertString(password)))
                .build();
        Hash hash = hashService.computeHash(request);
        return hash.toHex();
    }

    private DefaultHashService getDefaultHashService() {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setGeneratePublicSalt(true);
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
        hashService.setHashIterations(1024);
        return hashService;
    }
}
