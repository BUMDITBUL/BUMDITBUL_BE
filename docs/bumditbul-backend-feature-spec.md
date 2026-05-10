# 범딧불 백엔드 기능명세서 (최종본)

## 공통 사항

| 항목 | 내용 |
| --- | --- |
| 인증 방식 | JWT Bearer Token |
| 토큰 구성 | accessToken (단기) + refreshToken (장기) |
| 응답 형식 | Content-Type: application/json |
| 에러 응답 | `{ "code": "ERROR_CODE", "message": "설명" }` |
| 날짜 형식 | YYYY-MM-DD (ISO 8601) |
| 이미지 저장 | S3 등 외부 스토리지, DB에는 URL만 저장 |

---

## 1. 인증 (Auth)

### POST `/auth/email/send`
이메일 인증번호 발송 (회원가입 / 비밀번호 찾기 공통)

**Request**
```json
{ "email": "user@example.com" }
```

**Response**
```json
{ "message": "인증번호가 전송되었습니다." }
```

- 6자리 코드 생성, 유효시간 5분
- 이미 가입된 이메일이면 `"isDuplicate": true` 포함

### POST `/auth/email/verify`
인증번호 확인

**Request**
```json
{ "email": "user@example.com", "code": "123456" }
```

**Response**
```json
{ "verified": true }
```

### POST `/auth/signup`
회원가입

**Request**
```json
{ "email": "user@example.com", "password": "pass1234!" }
```

**Response**
```json
{ "accessToken": "...", "refreshToken": "..." }
```

- 비밀번호: 8자 이상 + 특수문자 포함
- 이메일 인증 완료 상태 필수

### POST `/auth/login`
로그인

**Request**
```json
{ "email": "user@example.com", "password": "pass1234!" }
```

**Response**
```json
{ "accessToken": "...", "refreshToken": "..." }
```

### POST `/auth/token/refresh`
AccessToken 재발급

**Request**
```json
{ "refreshToken": "..." }
```

**Response**
```json
{ "accessToken": "..." }
```

### POST `/auth/logout`
로그아웃 (refreshToken 무효화)

**Request**
```json
{ "refreshToken": "..." }
```

### PATCH `/auth/password/reset`
비밀번호 재설정

**Request**
```json
{ "email": "user@example.com", "newPassword": "newpass1!" }
```

### DELETE `/auth/withdraw`
회원탈퇴 (모든 데이터 삭제)

---

## 2. 온보딩

### GET `/onboarding/status`
온보딩 완료 여부 조회

**Response**
```json
{ "completed": false, "step": 1 }
```

### PATCH `/onboarding/profile`
닉네임 + 학교 저장

**Request**
```json
{ "nickname": "홍길동", "school": "대전고등학교" }
```

- 닉네임: 2~5자 한글
- 학교: 선택값

---

## 3. 사용자 프로필

### GET `/users/me`
내 프로필 조회

**Response**
```json
{
  "id": 1,
  "nickname": "홍길동",
  "school": "대전고등학교",
  "profileImageUrl": "https://..."
}
```

### PATCH `/users/me`
프로필 수정

**Request**
```json
{ "nickname": "홍길동", "school": "대전고등학교" }
```

### POST `/users/me/profile-image`
프로필 이미지 업로드

**Response**
```json
{ "profileImageUrl": "https://..." }
```

---

## 4. 과목별 성적 (Subject Grade)

### GET `/subjects`
과목 목록 조회

**Response**
```json
[
  { "id": 1, "name": "수학", "difficulty": "상" },
  { "id": 2, "name": "영어", "difficulty": "중" }
]
```

### PUT `/subjects`
과목 목록 저장/수정

**Request**
```json
[
  { "name": "수학", "difficulty": "상" },
  { "name": "영어", "difficulty": "중" }
]
```

- 최대 9개
- 저장 시 일정 재생성 트리거 (하루 2회 제한)

---

## 5. 시험범위 (Exam Range)

### GET `/exam-ranges`
시험범위 조회

**Response**
```json
[
  {
    "id": 1,
    "subject": "수학",
    "startPage": 10,
    "endPage": 100,
    "examDate": "2026-05-11",
    "difficulty": "상",
    "materials": ["교과서", "문제집"]
  }
]
```

### PUT `/exam-ranges`
시험범위 저장

**Request**
```json
[
  {
    "subject": "수학",
    "startPage": 10,
    "endPage": 100,
    "examDate": "2026-05-11",
    "difficulty": "상",
    "materials": ["교과서", "문제집"]
  }
]
```

- 최대 9개 과목
- 저장 시 일정 재생성 트리거

---

## 6. 일정 생성 및 분배

### POST `/schedule/generate`
일정 생성 요청

**Response**
```json
{ "status": "generating" }
```

- 하루 최대 2회 제한
- 비동기 처리 + SSE 사용

### GET `/schedule/status` (SSE)
상태 스트림

```text
event: status
data: { "status": "generating" }

event: status
data: { "status": "completed" }

event: status
data: { "status": "error" }
```

### GET `/schedule/regen-count`
재생성 횟수 조회

**Response**
```json
{ "used": 1, "max": 2, "remaining": 1 }
```

### 분배 로직
- 학습 가능 날짜 = (시험일 - 오늘) - 3일
- 난이도 가중치
  - 상: 1.3
  - 중: 1.0
  - 하: 0.7
- 복습 기간 (시험 3일 전)
  - 상: 2시간
  - 중: 1.5시간
  - 하: 1시간
- 학습 가능 날짜 ≤ 3일 → 최대 학습
- 0~1일 → 전체 범위 하루에 배정

---

## 7. 캘린더 & 일일 할 일

### GET `/schedule/calendar`
월별 일정

**Response**
```json
{
  "2026-04-28": {
    "total": 3,
    "done": 1,
    "completed": false,
    "isReview": false
  }
}
```

### GET `/schedule/daily`
일일 할 일

**Response**
```json
[
  {
    "id": 1,
    "subject": "수학",
    "material": "교과서",
    "startPage": 10,
    "endPage": 18,
    "difficulty": "상",
    "done": false,
    "isReview": false
  }
]
```

- 난이도 높은 순 정렬
- 일정 없으면 `[]`

### PATCH `/schedule/{id}/done`
완료 토글

**Request**
```json
{ "done": true }
```

**Response**
```json
{ "id": 1, "done": true }
```

### 자동 재분배
- 매일 00시 재분배
- 미완료 항목만 재분배
- 완료 항목 유지

---

## 8. 시험 D-Day

### GET `/users/me/exam-date`

**Response**
```json
{ "examDate": "2026-05-11" }
```

---

## API 목록 요약

| 메서드 | 엔드포인트 | 설명 |
| --- | --- | --- |
| POST | /auth/email/send | 인증번호 발송 |
| POST | /auth/email/verify | 인증 확인 |
| POST | /auth/signup | 회원가입 |
| POST | /auth/login | 로그인 |
| POST | /auth/token/refresh | 토큰 재발급 |
| POST | /auth/logout | 로그아웃 |
| PATCH | /auth/password/reset | 비밀번호 재설정 |
| DELETE | /auth/withdraw | 회원탈퇴 |
| GET | /onboarding/status | 온보딩 조회 |
| PATCH | /onboarding/profile | 프로필 저장 |
| GET | /users/me | 프로필 조회 |
| PATCH | /users/me | 프로필 수정 |
| POST | /users/me/profile-image | 이미지 업로드 |
| GET | /users/me/exam-date | 시험일 조회 |
| GET | /subjects | 과목 조회 |
| PUT | /subjects | 과목 저장 |
| GET | /exam-ranges | 시험범위 조회 |
| PUT | /exam-ranges | 시험범위 저장 |
| POST | /schedule/generate | 일정 생성 |
| GET | /schedule/status | 상태 조회 |
| GET | /schedule/regen-count | 횟수 조회 |
| GET | /schedule/calendar | 캘린더 |
| GET | /schedule/daily | 일일 할 일 |
| PATCH | /schedule/{id}/done | 완료 처리 |
