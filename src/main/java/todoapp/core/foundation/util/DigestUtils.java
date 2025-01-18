package todoapp.core.foundation.util;

import todoapp.core.foundation.SystemException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 해시 함수(hash function) 유틸리티 클래스이다.
 *
 * @author springrunner.kr@gmail.com
 */
public interface DigestUtils {

    /**
     * SHA-256 알고리즘으로 입력된 문자열을 해시 값을 생성한다.
     *
     * @param value 대상 문자열
     * @return 해시된 문자열
     */
    static String sha256(String value) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            return new String(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException error) {
            throw new SystemException("SHA-256 algorithm not available", error);
        }
    }

}
