package todoapp;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JdkLocaleTests {

  final static Logger log = LoggerFactory.getLogger(JdkLocaleTests.class);

  public static void main(String[] args) {
    // 보편적으로 국제 표준화 기구에서 정의한 2자리 언어 코드와 2자리 국가 코드로 구성합니다.
    logging(new Locale("ko", "KR"));

    // 주요 나라에 대해 상수 코드를 가지고 있어 편리하게 사용할 수 있는데, 다음과 같이 사용할 수 있습니다.
    logging(Locale.KOREA);
    logging(Locale.US);
    logging(Locale.UK);

    // JVM에 설정된 기본 값을 사용 할 수 있습니다. JVM 환경변수에 설정된 값이나 호스트인 운영체제 설정 값 등을 통해 만들어집니다.
    logging(Locale.getDefault());
  }

  static void logging(Locale locale) {
    log.info("Locale: {}", locale.toString());
    log.info("Language: {}, DisplayLanguage: {}", locale.getLanguage(), locale.getDisplayLanguage());
    log.info("Country: {}, DisplayCountry: {}", locale.getCountry(), locale.getDisplayCountry());
    log.info("--------------------------------------------------------");
  }

}
