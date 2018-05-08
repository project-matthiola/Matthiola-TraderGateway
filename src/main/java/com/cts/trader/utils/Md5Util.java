package com.cts.trader.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /**利用MD5进行加密*/
    static public String encoderByMd5(String str) {
        //确定计算方法
        MessageDigest md5;
        String md5str = null;
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            md5 = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = str.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md5.digest(input);
            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5str;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toLowerCase();
    }
}
