# Contract

클라이언트와 서버 간의 API 계약을 정의하고 관리하는 디렉토리입니다.

## 목적

구현체(client/server)와 분리하여 API 계약을 독립적으로 관리합니다. 모든 API 명세는 이 디렉토리에서만 관리하며, 변경 사항은 Git으로 추적합니다.

## 구조

```
contract/
├── openapi/              # OpenAPI 3.x 명세
│   ├── todoapp-api.yaml  # Todoapp REST API 명세
│   ├── index.html        # Swagger UI 뷰어
│   ├── serve.sh          # HTTP 서버 (macOS/Linux)
│   └── serve.bat         # HTTP 서버 (Windows)
└── README.md
```

## 명세 보기

### Swagger UI로 보기

**macOS/Linux:**
```bash
cd contract/openapi
./serve.sh          # http://localhost:3000
./serve.sh 8080     # 포트 지정
```

**Windows:**
```cmd
cd contract\openapi
serve.bat           # http://localhost:3000
serve.bat 8080      # 포트 지정
```

브라우저에서 표시된 URL(기본 http://localhost:3000)을 열면 API 문서를 확인할 수 있습니다.

### YAML 직접 편집

`openapi/todoapp-api.yaml` 파일을 직접 수정하여 API 명세를 관리합니다.
