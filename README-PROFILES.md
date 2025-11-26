# 환경별 프로파일 설정 가이드

## 프로파일 종류

- **local**: 로컬 개발 환경 (`application-local.properties`)
- **dev**: 개발 서버 환경 (`application-dev.properties`)
- **prod**: 프로덕션 환경 (`application-prod.properties`)

## IntelliJ IDEA에서 프로파일 설정 방법

### 방법 1: Run Configuration에서 설정

1. `Run` → `Edit Configurations...`
2. `AgentApplication` 실행 설정 선택
3. `Environment variables` 또는 `VM options`에 추가:
   ```
   -Dspring.profiles.active=local
   ```
4. 또는 `Active profiles` 필드에 `local` 입력

### 방법 2: application-local.properties 사용

1. `src/main/resources/application-local.properties` 파일이 있는지 확인
2. 파일이 없다면 `application-local.properties.example`을 복사하여 생성
3. DB 정보 등 필요한 설정 입력
4. IntelliJ에서 프로파일 활성화 (방법 1 참조)

## 환경변수로 설정 (프로덕션 권장)

```bash
export DB_URL=jdbc:mariadb://your-db-host:3306/ai_agent
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export FASTAPI_URL=http://your-fastapi-host:8000
export CORS_ALLOWED_ORIGINS=http://your-frontend-url
```

## 현재 설정 확인

애플리케이션 시작 시 로그에서 다음을 확인:
```
No active profile set, falling back to 1 default profile: "default"
```
→ 프로파일이 설정되지 않음

```
The following profiles are active: local
```
→ 프로파일이 정상적으로 활성화됨

