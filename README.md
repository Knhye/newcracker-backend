# 뉴크래커 — AI 기반 개인화 뉴스 요약·트렌드 분석 서비스

> GroqAI를 활용해 뉴스를 자동 요약·분류하고, 사용자 관심사에 맞는 뉴스와 금주 트렌드를 제공하는 **백엔드 API 서버**입니다.

---

## 기술 스택

| 분류 | 기술 |
|---|---|
| Framework | NestJS 11 |
| Language | TypeScript 5 |
| ORM | TypeORM 0.3 |
| Database | PostgreSQL |
| Auth | JWT (Access + Refresh Token) |
| Cache | Redis (ioredis) |
| AI | Groq API (Llama 3.1 8B / Llama 3.3 70B) |
| External API | Naver News Open API |
| Crawler | Cheerio |
| API Docs | Swagger (OpenAPI 3) |

---

## 주요 기능

- **뉴스 크롤링** — 네이버 뉴스 Open API 기반 최신 뉴스 수집, og 태그로 썸네일·언론사 자동 추출
- **AI 자동 분류** — Groq Llama 3.3 70B로 뉴스 제목·설명을 6개 카테고리로 자동 분류
- **AI 요약** — Groq Llama 3.1 8B로 뉴스 본문 5줄 요약 및 핵심 키워드 추출
- **금주 트렌드** — 카테고리(선택)별 이번 주 이슈 트렌드를 AI가 자연어로 분석
- **인기 뉴스** — 조회수 기반 인기 뉴스 TOP 5 제공
- **최근 본 뉴스** — Redis Sorted Set으로 사용자별 최근 조회 뉴스 최대 20개 추적 (7일 보관)
- **뉴스 검색** — 키워드 기반 뉴스 검색 (네이버 API 연동)
- **유사 뉴스 링크** — 상세 조회 시 핵심 키워드로 관련 뉴스 링크 자동 수집
- **캐시 스탬피드 방지** — 동일 카테고리 동시 요청 시 크롤링 1회만 실행 후 공유
- **인증** — 이메일/비밀번호 회원가입·로그인, Access/Refresh Token 재발급

---

## 뉴스 카테고리

| 코드 | 설명 |
|---|---|
| `POLITICS` | 정치 |
| `ECONOMY` | 경제 |
| `SOCIETY` | 사회 |
| `LIFE` | 생활·문화 |
| `IT_SCIENCE` | IT·과학 |
| `WORLD` | 세계 |

---

## 시작하기

### 사전 요구사항

- Node.js 20+
- PostgreSQL
- Redis

### 설치

```bash
npm install
```

### 환경 변수 설정

프로젝트 루트에 `.env` 파일을 생성하고 아래 항목을 설정합니다.

```env
# Database
DATABASE_URL=postgresql://USER:PASSWORD@HOST:5432/DB_NAME

# JWT
JWT_SECRET=your_jwt_secret
JWT_REFRESH_SECRET=your_refresh_secret

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# Naver News API
NAVER_CLIENT_ID=your_naver_client_id
NAVER_CLIENT_SECRET=your_naver_client_secret

# Groq AI
GROQ_API_KEY=your_groq_api_key

# 환경 구분 (prod 설정 시 Redis TLS 활성화)
NODE_ENV=development
```

### 데이터베이스 마이그레이션

```bash
npx typeorm migration:run -d src/data-source.ts
```

---

## 실행

```bash
# 개발 (watch 모드)
npm run start:dev

# 프로덕션 빌드 후 실행
npm run build
npm run start:prod
```

서버가 실행되면 Swagger UI에서 API 문서를 확인할 수 있습니다.

```
http://localhost:5000/api/v1/docs
```

---

## API 개요

| 태그 | 엔드포인트 | 설명 |
|---|---|---|
| **Auth** | `POST /auth/signup` | 회원가입 |
| | `POST /auth/login` | 로그인 |
| | `POST /auth/refresh` | Access / Refresh Token 재발급 |
| **News** | `GET /news/latest?category=` | 최신 뉴스 조회 (카테고리 선택) |
| | `GET /news/popular` | 인기 뉴스 TOP 5 (조회수 기준) |
| | `POST /news/detail` | 뉴스 상세 조회 + DB 저장 + 조회수 증가 |
| | `GET /news/recent` | 최근 본 뉴스 조회 (최대 20개) |
| | `GET /news?category=&page=` | 카테고리별 뉴스 목록 (페이지당 10개, 최대 10페이지) |
| | `GET /news/trend?category=` | 금주 뉴스 트렌드 AI 분석 |
| | `GET /news/search?q=` | 키워드 뉴스 검색 |
| **User** | `GET /user/me` | 내 정보 조회 |
| | `PATCH /user/me` | 프로필 수정 (이름, 관심 카테고리) |
| | `PUT /user/me/password` | 비밀번호 변경 |
| | `DELETE /user/me` | 회원 탈퇴 |

> News·User 엔드포인트는 모두 JWT Bearer 인증이 필요합니다.

---

## 스크립트

| 명령 | 설명 |
|---|---|
| `npm run start:dev` | 개발 서버 실행 (watch) |
| `npm run build` | 프로덕션 빌드 |
| `npm run start:prod` | 프로덕션 실행 |
| `npm run test` | 단위 테스트 |
| `npm run test:cov` | 커버리지 포함 테스트 |
| `npm run lint` | ESLint 검사 및 자동 수정 |

---

## 프로젝트 구조

```
src/
├── auth/          # 인증 (JWT, Refresh Token)
├── news/          # 뉴스 크롤링·캐싱·AI 요약·분류
│   ├── services/
│   │   ├── news.service.ts          # 비즈니스 로직
│   │   ├── news-crawling.service.ts # 네이버 API 크롤링
│   │   ├── news-cache.service.ts    # Redis 캐싱
│   │   └── ai.service.ts            # Groq AI 호출
│   ├── entities/
│   └── dto/
├── user/          # 사용자 정보 관리
├── company/       # 언론사 자동 등록·조회
└── common/        # 공통 데코레이터, 가드
```

---

## 라이선스

Private
