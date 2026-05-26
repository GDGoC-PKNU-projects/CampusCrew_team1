# BE-A STEP 2

## 이 STEP에서 왜 이걸 배우는가

이 단계부터 로그인은 단순한 API 하나가 아니라 서비스 전체를 보호하는 기준이 된다.  
JWT나 필터가 어렵게 느껴질 수 있지만, 핵심은 "로그인 성공 후 받은 정보를 다음 요청에서 어떻게 검증하느냐"를 이해하는 것이다.

## 이번 프로젝트에서 어디에 쓰는가

- 회원가입
- 로그인
- `me` 조회
- 보호 API 접근
- 인증 실패 처리

## 먼저 이해할 핵심 개념

- 비밀번호는 그대로 저장하지 않고 해시해서 비교한다.
- 로그인 성공 시 토큰을 발급하고 이후 요청에서 검증한다.
- 인증 필터는 요청이 들어올 때 먼저 검사하는 문지기 역할을 한다.
- `401`과 `403`은 의미가 다르다.
- 인증 실패 응답도 프론트와 맞는 형식이어야 한다.

## 꼭 알아야 할 용어

| 용어 | 쉬운 설명 |
| --- | --- |
| JWT | 로그인 후 발급받아 이후 요청에서 신원을 증명하는 토큰 |
| 해시 | 원문 비밀번호를 그대로 저장하지 않기 위한 변환 방식 |
| 인증 필터 | 요청을 처리하기 전에 인증 여부를 검사하는 단계 |
| Security Context | 현재 인증된 사용자를 보관하는 공간 |
| 401 | 로그인 정보가 없거나 잘못된 경우 |
| 403 | 로그인은 했지만 권한이 없는 경우 |

## 추천 학습 순서

1. `API_SPEC`에서 로그인/`me` 응답 구조를 다시 본다.
2. JWT가 어떤 흐름으로 쓰이는지 먼저 읽는다.
3. Spring Security가 인증/인가를 어떻게 다루는지 본다.
4. 비밀번호 해시와 인증 필터 개념을 읽는다.
5. `BE_A_GUIDE`의 STEP 2를 다시 읽으며 프로젝트에 연결한다.

## 먼저 읽을 문서

1. [BE_A_GUIDE](../../backend/BE_A_GUIDE.md)
2. [API_SPEC](../../backend/API_SPEC.md)
3. [PLAN](../../common/PLAN.md)

## 외부 자료 링크

- Spring Security 공식 문서: [https://docs.spring.io/spring-security/reference/](https://docs.spring.io/spring-security/reference/)
- jwt.io 소개: [https://jwt.io/introduction/](https://jwt.io/introduction/)
- Spring Security 로그인 예시: [https://www.baeldung.com/spring-security-login](https://www.baeldung.com/spring-security-login)
- Spring Security 인증/회원가입 시리즈: [https://www.baeldung.com/spring-security-authentication-and-registration](https://www.baeldung.com/spring-security-authentication-and-registration)

## 이번 STEP에서 바로 확인할 프로젝트 연결 포인트

| 항목 | 지금 확인할 것 |
| --- | --- |
| 로그인 성공 응답 | 토큰과 사용자 정보가 어떻게 내려오는지 본다. |
| 헤더 규칙 | `Authorization: Bearer {token}` 형식을 확인한다. |
| `me` 조회 | 토큰으로 현재 사용자를 확인하는 흐름을 읽는다. |
| 실패 처리 | `401`과 `403`이 언제 나오는지 정리한다. |

## 자주 막히는 지점

- JWT 내용을 다 외워야 구현할 수 있다고 생각한다.
- 비밀번호 저장과 비밀번호 비교를 같은 작업으로 본다.
- 로그인 요청에도 토큰이 필요하다고 착각한다.
- `401`과 `403`을 아무 때나 섞어 쓴다.

## 가볍게 해볼 것

- 로그인 성공 후 브라우저와 서버 사이에 어떤 데이터가 오가는지 순서대로 적어 본다.
- 보호 API 요청 시 헤더에 무엇이 들어가는지 적어 본다.
- 인증 실패와 권한 실패의 예시를 하나씩 적어 본다.

## 핵심 질문

1. 회원가입, 로그인, `me` 조회 API의 역할을 각각 설명할 수 있는가
2. JWT는 왜 로그인 이후 요청을 검증하는 데 필요한가
3. STEP 2에서 서버 초기 셋팅이 같이 진행되는 이유를 설명할 수 있는가

## 이번 주 정리 메모

> [!NOTE]
> 아래 내용을 직접 적으면서 이번 주 이해도를 점검한다.
> - 로그인 성공 후 서버가 돌려줄 값: `{ success: true, data: { accessToken: "jwt-token", user: { id, name, email } }, message: "로그인에 성공했습니다." }`
> - 다음 요청에서 인증을 확인하는 방식: `Authorization: Bearer {accessToken}` 헤더로 토큰을 보내면, 서버의 JWT 인증 필터가 토큰을 검증해 Security Context에 사용자 정보를 저장한다.
> - 프론트와 먼저 맞춰야 할 응답 필드: `accessToken`, `user.id`, `user.name`, `user.email`

## 체크리스트

- [x] JWT 인증 흐름을 큰 단계 기준으로 설명할 수 있다.
- [x] 비밀번호 해시가 왜 필요한지 이해했다.
- [x] 인증 필터가 왜 필요한지 설명할 수 있다.
- [x] `401`과 `403`의 차이를 설명할 수 있다.
- [x] STEP 2 구현 문서를 읽고 인증 흐름을 따라갈 수 있다.
