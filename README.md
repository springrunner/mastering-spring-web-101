# 스프링 웹 마스터하기 템플릿 프로젝트

스프링 웹 마스터하기(부제: Mastering Spring Web 101) 강좌는 [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)와 [Spring Boot](https://spring.io/projects/spring-boot)로 웹 애플리케이션 서버 사이드(Server-side)를 직접 개발하며 학습하는 워크숍 형식의 강좌입니다. 참가자는 Spring MVC의 핵심인 HTTP 요청 연결, 데이터 바인딩, 응답 및 예외 처리 방법을 배우고 경험 할 수 있습니다..

강좌에 대한 자세한 소개는 [여기](https://edu.nextstep.camp/c/ygVWPEgo)에서 볼 수 있습니다.

## Todoapp

`Todoapp` 웹 애플리케이션은 할일 관리 기능을 제공하는 웹 애플리케이션입니다. 사용자는 할일을 등록, 변경, 완료 및 삭제할 수 있으며, 할일을 CSV 파일로 다운로드 받을 수 있습니다. 추가적으로 사용자 로그인 및 로그아웃, 프로필 이미지 변경 기능을 포함합니다.

<p align="center">
  <img width="480px" src=".README/todoapp.png">
</p>

참가자는 제공되는 애플리케이션 정의서와 Web API 명세서, 그리고 웹 클라이언트로 `Todoapp` 웹 애플리케이션에 서버 사이드를 개발합니다. 가이드 러너가 라이브 코딩과 함께 Spring MVC(또는 Spring Boot)에 기능을 설명해주면, 참가자는 해당 코드를 직접 작성하고 실행-테스트 하는 과정을 반복하며 완전한 웹 애플리케이션을 개발해보는 방식으로 진행됩니다.

## 템플릿

* [client](./client/)는 웹 클라이언트로 웹 기술(HTML, CSS, JavaScript)과 [Thymeleaf](https://www.thymeleaf.org/) 형식으로 작성되었습니다.
* [contract](./contract/)는 클라이언트와 서버 간의 API 계약을 정의하고 관리하는 OpenAPI 명세가 포함되어 있습니다.
* [server](./server/)는 Spring MVC와 Spring Boot 기반으로 작성된 서버 템플릿 코드베이스입니다.

## 학습자료 및 환경

강좌에서 제공되는 학습 자료 중 일부를 [여기](https://github.com/springrunner/learn-spring-programming)에서 볼 수 있습니다. 그리고 사용하는 JVM 플랫폼 및 프레임워크, 라이브러리와 도구는 아래와 같습니다.

- Java SE 21 및 Jakarta EE 10을 사용합니다.
- Spring MVC 6.x, Spring Boot 3.x를 사용합니다.
- 빌드 도구로 그레이들(Gradle)을 사용합니다.
- IDE는 IntelliJ IDEA를 사용합니다.
