package com.cts.trader.utils;

public class PasswordUtil {
    static public String getEncryptedPassword(String plainPassword) {
        return Md5Util.encoderByMd5(plainPassword).toLowerCase();
    }
    static public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return getEncryptedPassword(plainPassword).equals(encryptedPassword);
    }
}
