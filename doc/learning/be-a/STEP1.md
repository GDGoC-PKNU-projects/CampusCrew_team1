# BE-A STEP 1

## 이 STEP에서 왜 이걸 배우는가

BE-A는 인증과 팀 도메인의 입구를 담당하므로, 프로젝트 초반에 가장 먼저 서버의 큰 구조를 이해해야 한다.  
Spring 코드보다 먼저 "HTTP 요청이 어떻게 들어오고, 어떤 계층을 지나 응답이 나가는지"를 이해하는 것이 중요하다.

## 이번 프로젝트에서 어디에 쓰는가

- 회원가입 API
- 로그인 API
- `me` 조회
- 팀 생성과 팀 참여의 기본 구조
- 공통 응답 형식

## 먼저 이해할 핵심 개념

- 클라이언트와 서버는 HTTP 요청/응답으로 통신한다.
- 백엔드는 한 파일에 모든 걸 넣지 않고 계층을 나눈다.
- 인증이 필요한 API와 아닌 API는 구분해야 한다.
- JSON 응답 구조는 프론트와의 계약이다.
- 팀 도메인은 이후 모든 팀 하위 리소스의 기준축이 된다.

## 꼭 알아야 할 용어

| 용어 | 쉬운 설명 |
| --- | --- |
| HTTP | 브라우저와 서버가 대화하는 방식 |
| REST API | URL과 HTTP 메서드로 자원을 다루는 방식 |
| Controller | 요청을 받고 응답을 돌려주는 입구 |
| Service | 실제 비즈니스 규칙을 처리하는 계층 |
| Repository | 데이터베이스 조회와 저장을 담당하는 계층 |
| DTO | 요청/응답용 데이터 모양 |
| 인증 | 사용자가 누구인지 확인하는 것 |
| 인가 | 이 사용자가 해당 기능을 해도 되는지 확인하는 것 |

## 추천 학습 순서

1. `PRD`, `API_SPEC`, `ERD`를 보고 어떤 데이터를 다루는 서비스인지 이해한다.
2. HTTP 요청/응답과 상태 코드 기본을 읽는다.
3. Spring Boot 프로젝트가 보통 어떤 계층으로 나뉘는지 본다.
4. `BE_A_GUIDE`의 STEP 1을 읽으며 인증/팀 도메인에 대입한다.
5. STEP 2에서 JWT를 볼 준비를 한다.

## 먼저 읽을 문서

1. [PRD](../../common/PRD.md)
2. [API_SPEC](../../backend/API_SPEC.md)
3. [ERD](../../backend/ERD.md)
4. [BE_A_GUIDE](../../backend/BE_A_GUIDE.md)

## 외부 자료 링크

- MDN HTTP Overview: [https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview)
- Spring Boot 공식 문서: [https://docs.spring.io/spring-boot/reference/index.html](https://docs.spring.io/spring-boot/reference/index.html)
- Spring REST Guide: [https://spring.io/guides/gs/rest-service/](https://spring.io/guides/gs/rest-service/)
- Spring Boot 문서 안내: [https://docs.spring.io/spring-boot/documentation.html](https://docs.spring.io/spring-boot/documentation.html)

## 이번 STEP에서 바로 확인할 프로젝트 연결 포인트

| 항목 | 지금 확인할 것 |
| --- | --- |
| 공통 응답 구조 | 성공/실패 응답의 큰 모양을 읽는다. |
| 공개 API | 로그인, 회원가입처럼 인증 없이 열리는 API를 확인한다. |
| 보호 API | 팀 내부 기능이 왜 보호되어야 하는지 본다. |
| 팀 구조 | 팀 생성, 목록, 참여 API가 왜 초기 축인지 이해한다. |

## 자주 막히는 지점

- Controller가 모든 일을 해야 한다고 생각한다.
- 엔터티를 그대로 응답으로 보내도 된다고 생각한다.
- 인증과 인가를 같은 개념으로 본다.
- 상태 코드와 메시지 역할을 분리하지 못한다.

## 가볍게 해볼 것

- `API_SPEC`에서 공개 API와 보호 API를 나눠 적어 본다.
- 로그인 응답과 팀 목록 응답에 어떤 필드가 들어가는지 적어 본다.
- Controller, Service, Repository가 각각 무슨 역할인지 한 줄씩 적어 본다.

## 핵심 질문

1. HTTP 요청이 Controller, Service, Repository를 어떻게 지나가는지 설명할 수 있는가
2. 인증 도메인을 왜 다른 도메인보다 먼저 잡아야 하는가
3. 공개 API와 보호 API의 차이를 설명할 수 있는가

## 이번 주 정리 메모

> [!NOTE]
> 아래 내용을 직접 적으면서 이번 주 이해도를 점검한다.
> - 요청이 서버 안에서 흐르는 순서: Request-> Controller -> Service -> Repository -> Service -> Controller -> Response
> - 각 계층의 역할: Controller: 클라이언트와 통신을 주고받는다. Service: 컨트롤러가 받은 Request를 서비스 로직에 대입하고, Repository와 상호작용 후, Response를 만들어서 내보낸다. Repository: DB에 간접적으로 상호작용 할수 있게 해준다.
> - 내가 아직 헷갈리는 백엔드 용어: DTO 아직 어떻게 쓰라는건지 잘 모르겠다.

## 체크리스트

- [x] HTTP 요청/응답과 상태 코드의 기본 역할을 이해했다.
- [x] Controller, Service, Repository의 역할 차이를 설명할 수 있다.
- [x] 인증과 인가의 차이를 설명할 수 있다.
- [x] 공통 응답 형식이 왜 필요한지 이해했다.
- [x] STEP 2에서 인증 흐름을 구현할 준비가 되었다.
