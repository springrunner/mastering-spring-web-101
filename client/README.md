# Todoapp Client-side

> 이 프로젝트는 [TodoMVC App Template](https://github.com/tastejs/todomvc-app-template/)을 기반으로, 웹 기술(HTML, CSS, JavaScript)과 [Thymeleaf](https://www.thymeleaf.org/)로 개발된 웹 클라이언트입니다.

Todoapp 웹 클라이언트는 3개 페이지로 구성되어있습니다.

* **todos.html** : 할일 목록 페이지 - AJAX를 사용해 [Web API](https://en.wikipedia.org/wiki/Web_API)를 호출하고, 결과를 출력합니다.
* **login.html** : 사용자 로그인 페이지 - HTML 폼(form) 전송으로 사용자 로그인을 시도합니다.
* **error.html** : 오류 페이지 - 서버 발생오류에 담긴 모델을 출력합니다.

### 공통

* 모든 페이지(html)는 Thymeleaf 형식으로 작성되었습니다.
* 모든 페이지 하단에는 사이트 작성자와 설명을 노출하는 기능(푸터, Footer)이 포함되어 있습니다.
  - 서버에서 제공된 모델(Model)에 다음 키(Key)에 해당하는 값(Value)이 있으면 출력합니다.
    - `site.authour`: 사이트 작성자를 출력합니다.
    - `site.description`: 사이트 설명을 출력합니다.

### 할일 목록 페이지: todos.html

이 페이지는 할일 등록부터 완료, 목록 조회 등 다양한 기능이 순수한 자바스크립트로 동작하도록 작성되어 있습니다. 페이지의 기능이 동작하기 위해서는 다음과 같은 Web API가 필요합니다.

* `GET /api/todos`: 할일 목록 조회
* `POST /api/todos`: 새로운 할일 등록
* `PUT /api/todos/{todo.id}`: 등록된 할일 수정 또는 완료
* `DELETE /api/todos/{todo.id}`: 등록된 할일 삭제
* `GET /api/feature-toggles`: 확장 기능 활성화
* `GET /api/user/profile`: 로그인된 사용자 프로필 정보 조회
* `POST /api/user/profile-picture`: 로그인된 사용자 프로필 이미지 변경
* `GET /stream/online-users-counter`: 접속된 사용자 수 변경 이벤트 결과 출력
  - 이벤트 스트림은 [EventSource](https://developer.mozilla.org/en-US/docs/Web/API/EventSource)로 연결

> Web API 응답 상태코드가 40X([클라이언트 오류, Client Error](https://developer.mozilla.org/ko/docs/Web/HTTP/Status#%ED%81%B4%EB%9D%BC%EC%9D%B4%EC%96%B8%ED%8A%B8_%EC%97%90%EB%9F%AC_%EC%9D%91%EB%8B%B5)), 50X([서버 오류, Server error](https://developer.mozilla.org/ko/docs/Web/HTTP/Status#%EC%84%9C%EB%B2%84_%EC%97%90%EB%9F%AC_%EC%9D%91%EB%8B%B5))라면, 응답 바디에 담긴 오류 모델을 출력합니다. 보다 자세한 내용은 [error.html](#오류-페이지:-error.html) 을 참조바랍니다.

> [Todoapp Web APIs Document](https://app.swaggerhub.com/apis-docs/code-rain/todoapp/1.0.0-snapshot)에서 보다 상세한 WEB API 스펙을 확인할 수 있습니다.

할일 목록을 [CSV(Comma-separated values)](https://en.wikipedia.org/wiki/Comma-separated_values)형식으로 내려받을 목적으로 다음과 같이 서버를 호출합니다.

```
Http URL: /todos
Http Method: GET
Http Headers:
  Accept: text/csv
```

### 사용자 로그인 페이지: login.html

* HTML 폼으로 사용자이름(username)과 비밀번호(password)를 입력받도록 작성되었습니다.
* 로그인 버튼을 클릭하면 `POST /login`로 사용자 입력값(username, password)을 전송합니다.
* 서버에서 제공된 모델(Model)에 다음 키(Key)에 해당하는 값(Value)이 있으면 출력합니다.
  - `bindingResult`: [Spring BindingResult](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html) 객체에서 오류 내용을 출력합니다.
  - `message`: 서버에서 전달한 메시지가 있으면 출력합니다.

### 오류 페이지: error.html

* 타임리프(Thymeleaf)를 통해 오류 정보를 출력하도록 작성되었습니다.
* 서버에서 제공하는 모델(Model)에 다음 키(Key)에 해당하는 값(Value)이 있으면 출력합니다. 
  - `path`: 오류가 발생한 URL 경로(path)
  - `status`: HTTP 상태코드
  - `error`: 오류 발생 이유
    - `errors`: 스프링 BindingResult 내부에 모든 ObjectErrors 객체 목록
  - `message`: 오류 내용
  - `timestamp`: 오류 발생시간

## I. 프로젝트 구성

이 프로젝트는 웹 기술(HTML, CSS, JavaScript)과 타임리프(Thymeleaf)를 사용해 바닐라 자바스크립트(Vanilla JS)로 개발하고, 비트(Vite)로 관리하고 있습니다. 타임리프를 통해 빠르게 초기 화면을 구성하고, 바닐라 자바스크립트를 통해 사용자 인터랙션 및 동적인 기능을 구현하여 성능과 사용자 경험을 모두 향상시킵니다. 특히, 비트의 개발 서버와 HMR(Hot Module Replacement)은 바닐라 자바스크립트의 장점을 극대화하여 개발 생산성을 높이고, 쾌적한 개발 환경을 제공합니다.

> [Thymeleaf](https://www.thymeleaf.org/)는 강력한 자바 템플릿 엔진으로, 서버에서 동적으로 HTML을 생성합니다. 이를 통해 사용자는 첫 페이지 로딩 시 완전한 형태의 페이지를 빠르게 볼 수 있으며, 검색 엔진은 페이지 내용을 정확히 파악하여 검색 결과에 반영할 수 있습니다.
> 
> 바닐라 자바스크립트는 외부 라이브러리나 프레임워크 없이 순수 자바스크립트만으로 개발하는 방식입니다. 이는 불필요한 코드를 줄여 애플리케이션의 크기를 최소화하고, 로딩 속도를 향상시키는 데 기여합니다. 또한, 앱의 핵심 로직을 명확하게 파악하고 유지보수하기 용이하다는 장점이 있습니다.
> 
> [Vite](https://vitejs.dev)는 빠른 개발 서버 구축 및 빌드를 위한 도구입니다. 특히 Hot Module Replacement (HMR) 기능을 통해 코드 변경 사항을 실시간으로 반영하여 개발 과정을 훨씬 효율적으로 만들어 줍니다. 또한, 최신 웹 개발 트렌드를 반영하여 ES 모듈을 기본적으로 지원하고, 다양한 플러그인을 통해 확장성을 높일 수 있습니다.

### 디렉토리 구조

```
├── pages                    # HTML 소스 코드
├── public                   # 리소스 파일
├── src                      # JavaScript, CSS 소스 코드
├── dist                     # 빌드(`npm run build`) 명령으로 생성된 배포본
├── index.html
├── style.css
├── vite.config.js           # Vite 설정
├── package.json
└── package-lock.json
```

### 의존성 관리

패키지 의존성 관리를 위해 [NPM](https://nodejs.org/en/learn/getting-started/an-introduction-to-the-npm-package-manager/)을 사용하며, 클라이언트 개발에 사용된 의존성은 `package.json` 명세파일에 선언되어 있습니다.

### 프로젝트 설정
> 프로젝트 설정을 위해서 `NPM`이 설치되어 있어야 합니다.

```
❯ git clone https://github.com/springrunner/mastering-spring-web-101.git
❯ cd mastering-spring-web-101/client
❯ npm install
```

## II. 빌드 및 실행 방법

저장소를 복제하거나 압축 파일로 다운로드한 후 터미널에서 다음과 같은 방법으로 실행할 수 있습니다.

```
❯ git clone https://github.com/springrunner/mastering-spring-web-101.git
❯ cd mastering-spring-web-101/client
❯ npm run dev
```

빌드는 다음 명령을 통해 실행할 수 있습니다.

```
❯ npm run build
```

# III. 참고자료

* [TodoMVC App Template](https://github.com/tastejs/todomvc-app-template/)
* [Vite](https://vitejs.dev/)
* [Thymeleaf](https://www.thymeleaf.org/)
