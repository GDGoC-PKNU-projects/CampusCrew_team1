# BE-A STEP 3

## 이 STEP에서 왜 이걸 배우는가

팀 도메인은 이후 공지, 일정, 할 일, 회의록의 공통 바탕이 된다.  
이 단계에서 팀 생성, 참여, 멤버십 검증 흐름을 단단히 잡지 못하면 뒤 단계의 권한 검증도 계속 흔들린다.

## 이번 프로젝트에서 어디에 쓰는가

- 팀 생성 API
- 내 팀 목록 조회 API
- 팀 상세 조회 API
- 팀 참여 API
- 팀 멤버십 검증 공통 로직

## 먼저 이해할 핵심 개념

- 사용자와 팀의 관계 모델링
- 멤버십 엔터티가 필요한 이유
- 팀 생성과 팀장 권한 부여 흐름
- 참여 코드와 팀 ID의 역할 차이
- 팀 하위 API에서 재사용할 검증 로직

## 꼭 알아야 할 용어

- `membership`: 사용자가 어떤 팀에 속해 있는지 표현하는 관계 데이터
- `owner`: 팀 생성 직후 기본으로 부여되는 팀 관리자 역할
- `join code`: 팀 참여를 위한 보조 식별값
- `domain service`: 팀 도메인의 핵심 규칙을 처리하는 서비스 계층
- `authorization boundary`: 팀 내부 기능에 접근해도 되는지 나누는 경계

## 추천 학습 순서

1. [BE_A_GUIDE](../../backend/BE_A_GUIDE.md)의 `STEP 3`을 읽는다.
2. [API_SPEC](../../backend/API_SPEC.md)의 `5. 팀 API`를 읽는다.
3. [ERD](../../backend/ERD.md)의 `users`, `teams`, `team_members`를 읽는다.
4. 팀 생성부터 대시보드 진입까지 어떤 데이터가 필요한지 한 줄로 적어 본다.
5. "팀에 속한 사용자만 접근 가능"한 로직이 어디에 재사용될지 생각해 본다.

## 먼저 읽을 문서

1. [BE_A_GUIDE](../../backend/BE_A_GUIDE.md)
2. [API_SPEC](../../backend/API_SPEC.md)
3. [ERD](../../backend/ERD.md)
4. [PLAN](../../common/PLAN.md)

## 외부 자료 링크

- Spring Data JPA: [Repositories](https://docs.spring.io/spring-data/jpa/reference/repositories.html)
- Spring: [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- Baeldung: [JPA One to Many](https://www.baeldung.com/hibernate-one-to-many)
- Baeldung: [DTO Pattern](https://www.baeldung.com/java-dto-pattern)

## 이번 STEP에서 바로 확인할 프로젝트 연결 포인트

- [API_SPEC](../../backend/API_SPEC.md)에서 팀 생성 응답과 팀 목록 응답 필드가 프론트에서 그대로 쓰일 수 있는지 본다.
- [ERD](../../backend/ERD.md)의 팀 멤버 관계와 역할 값을 다시 확인한다.
- [PLAN](../../common/PLAN.md)의 `STEP 3` 종료 조건이 팀 생성 후 대시보드 진입이라는 점을 다시 본다.
- [FRONTEND_GUIDE](../../frontend/FRONTEND_GUIDE.md)의 `STEP 3`을 읽고 프론트가 어떤 흐름을 기대하는지 확인한다.

## 자주 막히는 지점

- 팀 생성과 동시에 멤버십 생성이 필요하다는 점을 놓친다.
- 팀 ID와 참여 코드를 같은 역할처럼 취급한다.
- 멤버십 검증을 여러 서비스에 복붙해서 나중에 기준이 어긋난다.
- 팀 상세 응답에 내부 엔터티 필드를 과하게 노출한다.

## 가볍게 해볼 것

- 팀 생성 성공 시 어떤 레코드가 생겨야 하는지 `팀`, `멤버십` 두 줄로 적어 본다.
- 팀 참여 실패 케이스를 2개만 적어 본다.
- 프론트가 팀 카드에 바로 쓸 필드 3~4개를 적어 본다.

## 핵심 질문

1. 팀 생성 시 왜 멤버십도 함께 만들어져야 하는가
2. 팀 ID와 참여 코드는 어떤 역할 차이가 있는가
3. 멤버십 검증 로직을 왜 여러 도메인에서 재사용해야 하는가

## 이번 주 정리 메모

> [!NOTE]
> 아래 내용을 직접 적으면서 이번 주 이해도를 점검한다.
> - 팀 생성 시 생기는 데이터: {"success":true, "data": {id, name, courceName, description, joinCode}, "message":"팀이 생성되었습니다.}
> - 팀 참여 실패 조건: 이미 참여 하려는 팀에 들어가 있는 경우
> - 재사용할 멤버십 검증 포인트: 팀 상세 조회, 팀 참여, 팀 생성

## 체크리스트

- [ ] 팀과 사용자 관계를 멤버십으로 설명할 수 있다.
- [ ] 팀 생성, 목록, 상세, 참여 흐름을 알고 있다.
- [ ] 팀 하위 API에서 멤버십 검증이 재사용되어야 한다는 점을 이해했다.
- [ ] 참여 코드와 팀 ID의 역할 차이를 설명할 수 있다.
