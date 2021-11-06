package todoapp.commons.util;

import todoapp.commons.SystemException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 해시 함수(hash function) 유틸리티
 *
 * @author springrunner.kr@gmail.com
 */
public interface DigestUtils {

    /**
     * SHA-256 알고리즘으로 입력된 문자열을 해시 값을 생성합니다.
     *
     * @param value 대상 문자열
     * @return
     */
    static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return new String(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException error) {
            throw new SystemException("SHA-256 알고리즘이 없습니다.", error);
        }
    }

}
