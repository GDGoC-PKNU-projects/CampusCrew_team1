# AI STEP 1 설계 정리

## 1. 기능별 입출력 표

| 기능 | 연결 화면 | 입력 필드 | 출력 필드 | 목적 |
|---|---|---|---|---|
| 회의록 액션 아이템 추출 | 회의록 작성/수정 화면 | teamId, content | items(title, assigneeHint, dueDateHint) | 회의록에서 실행 가능한 할 일 후보를 추출한다 |
| 문서 톤 변환 | 공지 작성/수정 화면 | teamId, content, tone | content | 공지 내용을 더 짧게, 정중하게, 평이하게 변환한다 |
| 진행 상황 요약 | 팀 대시보드 | teamId | summary | 팀의 최근 진행 상황을 요약한다 |
| 팀 문서 Q&A | 팀 대시보드 | teamId, question | answer, sources | 팀 문서를 검색해 질문에 답변한다 |

---

## 2. 기능별 입력 필드 상세

### 2.1 회의록 액션 아이템 추출

입력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| teamId | number | 팀 식별 ID |
| content | string | 회의록 원문 텍스트 |

출력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| title | string | 할 일 제목 |
| assigneeHint | string or null | 담당자 추정값 |
| dueDateHint | string(YYYY-MM-DD) or null | 마감일 추정값 |

---

### 2.2 문서 톤 변환

입력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| teamId | number | 팀 식별 ID |
| content | string | 공지 원문 |
| tone | string | 변환할 톤 값 |

허용 tone 값:

- SHORT
- FORMAL
- PLAIN

출력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| content | string | 변환된 공지 내용 |

---

### 2.3 진행 상황 요약

입력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| teamId | number | 팀 식별 ID |

내부 조회 데이터:

- recentNotices
- upcomingSchedules
- myTasks
- recentNotes

출력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| summary | string | 팀 진행 상황 요약 결과 |

---

### 2.4 팀 문서 Q&A

입력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| teamId | number | 팀 식별 ID |
| question | string | 사용자 질문 |

내부 조회 데이터:

- retrievedDocuments

출력 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| answer | string | 질문에 대한 답변 |
| sources | array | 답변 근거 문서 목록 |

sources 내부 필드:

| 필드명 | 타입 | 설명 |
|---|---|---|
| type | string | 문서 타입 |
| id | number | 문서 ID |
| title | string | 문서 제목 |

허용 type 값:

- NOTICE
- SCHEDULE
- TASK
- MEETING_NOTE

---

# 3. 프롬프트 초안 문서

## 3.1 회의록 액션 아이템 추출

### 역할

너는 회의록에서 실행 가능한 할 일 후보를 추출하는 AI다.

### 입력 데이터

- teamId
- content

### 출력 형식

```json
{
  "success": true,
  "data": {
    "items": [
      {
        "title": "string",
        "assigneeHint": "string or null",
        "dueDateHint": "YYYY-MM-DD or null"
      }
    ]
  },
  "message": null
}
```

### 규칙

- 회의록 내용 안에서만 할 일을 추출한다.
- 실행 가능한 작업만 추출한다.
- title은 반드시 포함한다.
- 담당자가 명확하지 않으면 assigneeHint는 null을 사용한다.
- 날짜가 명확하지 않으면 dueDateHint는 null을 사용한다.
- dueDateHint는 YYYY-MM-DD 형식만 사용한다.

### 금지사항

- 회의록에 없는 내용을 만들지 않는다.
- 불확실한 정보를 확정해서 작성하지 않는다.
- 담당자나 날짜를 임의로 추측하지 않는다.

---

## 3.2 문서 톤 변환

### 역할

너는 공지 문서를 지정된 톤으로 변환하는 AI다.

### 입력 데이터

- teamId
- content
- tone

### 허용 tone 값

- SHORT
- FORMAL
- PLAIN

### 출력 형식

```json
{
  "success": true,
  "data": {
    "content": "string"
  },
  "message": null
}
```

### 규칙

- 원문의 의미를 유지한다.
- 문장의 표현만 변경한다.
- tone 값에 맞게 문체를 변환한다.
- 자연스러운 한국어 문장으로 변환한다.

### 금지사항

- 원문에 없는 내용을 추가하지 않는다.
- 원문의 의미를 바꾸지 않는다.
- 허용되지 않은 tone 값을 사용하지 않는다.

---

## 3.3 진행 상황 요약

### 역할

너는 팀의 최근 진행 상황을 요약하는 AI다.

### 입력 데이터

- teamId
- recentNotices
- upcomingSchedules
- myTasks
- recentNotes

### 출력 형식

```json
{
  "success": true,
  "data": {
    "summary": "string"
  },
  "message": null
}
```

### 규칙

- 최근 진행 상황 중심으로 요약한다.
- 최근 공지, 다가오는 일정, 내 할 일, 최근 회의록 내용을 참고한다.
- 핵심 작업과 일정 위주로 정리한다.
- 너무 길거나 중복된 문장을 만들지 않는다.

### 금지사항

- 존재하지 않는 진행 상황을 만들지 않는다.
- 추측성 내용을 추가하지 않는다.
- 관련 없는 내용을 추가하지 않는다.

---

## 3.4 팀 문서 Q&A

### 역할

너는 팀 문서를 검색해서 질문에 답변하는 AI다.

### 입력 데이터

- teamId
- question
- retrievedDocuments

### 출력 형식

```json
{
  "success": true,
  "data": {
    "answer": "string",
    "sources": [
      {
        "type": "NOTICE | SCHEDULE | TASK | MEETING_NOTE",
        "id": "number",
        "title": "string"
      }
    ]
  },
  "message": null
}
```

### 규칙

- 검색된 문서를 기반으로만 답변한다.
- 답변에는 반드시 출처를 포함한다.
- sources는 최대 3개까지만 반환한다.
- 관련도가 높은 문서를 우선 사용한다.

### 금지사항

- 문서에 없는 내용을 추측해서 답변하지 않는다.
- 출처 없이 답변하지 않는다.
- 다른 팀 문서를 근거로 사용하지 않는다.

---

# 4. 실패 처리 표

| 실패 상황 | 에러 코드 | 처리 방법 | 사용자 메시지 |
|---|---|---|---|
| AI 응답이 비어 있음 | AI_001 | 실패 응답 반환 | AI 응답을 생성하지 못했습니다. |
| JSON 형식이 깨짐 | AI_002 | 실패 응답 반환 | AI 응답 형식이 올바르지 않습니다. |
| Q&A 검색 결과 없음 | AI_003 | 답변 생성 중단 | 관련 문서를 찾을 수 없습니다. |
| 액션 아이템 title 누락 | AI_004 | 실패 응답 반환 | 액션 아이템을 추출하지 못했습니다. |
| AI 서버 연결 실패 | AI_005 | 실패 응답 반환 | AI 서버에 연결할 수 없습니다. |

---

# 5. 실패 처리 공통 원칙

- 실패 응답은 ERROR_SPEC의 공통 에러 형식을 따른다.
- AI 실패는 일반 CRUD 기능에 영향을 주면 안 된다.
- Q&A는 관련 문서가 없으면 추측하지 않는다.
- 필수 필드가 없으면 실패 처리한다.

에러 응답 형식:

```json
{
  "success": false,
  "data": null,
  "message": "에러 메시지"
}
```

---

# 6. RAG 설계 메모

## 6.1 RAG 적용 대상

RAG는 팀 문서 Q&A 기능에만 적용한다.

적용 기능:

- 팀 문서 Q&A

적용하지 않는 기능:

- 회의록 액션 아이템 추출
- 문서 톤 변환
- 진행 상황 요약

---

## 6.2 검색 대상 문서

Q&A에서 검색 가능한 문서:

- 공지
- 일정
- 할 일 설명
- 회의록

문서 type 값:

- NOTICE
- SCHEDULE
- TASK
- MEETING_NOTE

---

## 6.3 검색 범위 규칙

- 문서는 같은 팀 내부에서만 검색한다.
- 다른 팀 문서는 검색하지 않는다.
- 검색 결과는 최대 3개까지만 사용한다.
- 관련도가 높은 문서를 우선 사용한다.

---

## 6.4 출처 반환 규칙

- 답변에는 반드시 출처를 포함한다.
- sources는 최대 3개까지만 반환한다.
- 출처 없이 답변하지 않는다.

---

## 6.5 검색 실패 규칙

- 관련 문서가 없으면 추측해서 답변하지 않는다.
- 검색 결과가 없으면 관련 문서를 찾을 수 없다고 응답한다.

---

## 6.6 현재 STEP에서 이해한 핵심

- RAG는 문서를 검색해서 답변 품질을 높이는 방식이다.
- Q&A 기능에만 RAG를 적용한다.
- 출처가 없는 답변은 허용하지 않는다.

---

# 7. 평가 기준표

## 7.1 회의록 액션 아이템 추출

| 평가 항목 | 좋은 결과 기준 |
|---|---|
| title 정확성 | 실제 회의록 내용 기반으로 할 일 제목을 추출한다 |
| assigneeHint 정확성 | 담당자가 명확할 때만 추출한다 |
| dueDateHint 정확성 | 날짜가 명확할 때만 추출한다 |
| hallucination(근거 없는 생성) 방지 | 회의록에 없는 할 일을 만들지 않는다 |
| JSON 형식 준수 | items 배열과 필수 필드를 유지한다 |

좋은 응답 예시:

```json
{
  "success": true,
  "data": {
    "items": [
      {
        "title": "로그인 API 테스트",
        "assigneeHint": "민수",
        "dueDateHint": null
      }
    ]
  },
  "message": null
}
```

---

## 7.2 문서 톤 변환

| 평가 항목 | 좋은 결과 기준 |
|---|---|
| 의미 유지 | 원문의 의미가 바뀌지 않는다 |
| tone 반영 | 요청한 tone 스타일이 반영된다 |
| 자연스러운 문장 | 어색하지 않은 한국어 문장을 생성한다 |
| 불필요한 추가 금지 | 원문에 없는 내용을 추가하지 않는다 |
| JSON 형식 준수 | content 필드를 유지한다 |

좋은 응답 예시:

```json
{
  "success": true,
  "data": {
    "content": "내일까지 과제를 제출해 주시기 바랍니다."
  },
  "message": null
}
```

---

## 7.3 진행 상황 요약

| 평가 항목 | 좋은 결과 기준 |
|---|---|
| 핵심 내용 포함 | 최근 진행 상황이 포함된다 |
| 요약 품질 | 너무 길거나 중복되지 않는다 |
| 정확성 | 실제 팀 데이터 기반으로 작성한다 |
| hallucination(근거 없는 생성) 방지 | 존재하지 않는 작업을 만들지 않는다 |
| JSON 형식 준수 | summary 필드를 유지한다 |

좋은 응답 예시:

```json
{
  "success": true,
  "data": {
    "summary": "이번 주 로그인 기능과 공지 기능 구현이 진행 중입니다."
  },
  "message": null
}
```

---

## 7.4 팀 문서 Q&A

| 평가 항목 | 좋은 결과 기준 |
|---|---|
| 질문 관련성 | 질문과 관련된 답변을 생성한다 |
| 출처 정확성 | 실제 검색 문서를 출처로 반환한다 |
| hallucination(근거 없는 생성) 방지 | 문서에 없는 내용을 만들지 않는다 |
| 출처 포함 여부 | sources 필드를 반드시 포함한다 |
| JSON 형식 준수 | answer와 sources 구조를 유지한다 |

좋은 응답 예시:

```json
{
  "success": true,
  "data": {
    "answer": "로그인 API를 먼저 구현하기로 결정했습니다.",
    "sources": [
      {
        "type": "MEETING_NOTE",
        "id": 12,
        "title": "4월 1주차 회의록"
      }
    ]
  },
  "message": null
}
```
