## 7장 분산 시스템을 위한 유일 ID 생성기 설계
대규모 분산 시스템에서는 각 요청이나 데이터 항목에 고유한 식별자(ID)를 부여하는 것이 필수적입니다. 

이 장에서는 분산 환경에서 유일한 ID를 생성하는 다양한 방법과 그 장단점을 살펴보며 최적의 설계 방안을 모색합니다.

## 문제 정의 및 설계 요구사항

효율적인 유일 ID 생성기를 설계하기 위해 다음과 같은 요구사항을 충족해야 합니다.

- **유일성 보장**: 생성된 ID는 시스템 전체에서 중복되지 않아야 합니다.
- **숫자 기반**: ID는 숫자로만 구성되어야 하며, 64비트로 표현 가능해야 합니다.
- **시간 정렬 가능성**: ID는 생성된 시간 순서대로 정렬 가능해야 합니다.
- **고성능 처리량**: 초당 최소 10,000개의 ID를 생성할 수 있어야 합니다.


## ID 생성 전략 비교

### 1. 다중 마스터 복제 (Multi-Master Replication)

**개념**: 각 데이터베이스 서버에 auto_increment를 설정하되, 증가 값을 서버 수만큼 증가시켜 유일한 ID를 생성합니다.

**예시**:

- 서버 1: 1, 4, 7, ...
- 서버 2: 2, 5, 8, ...
- 서버 3: 3, 6, 9, ...

**장점**:

- 간단한 구현으로 유일성 보장
- 서버 수에 따라 처리량 증가 가능

**단점**:

- 시간 순 정렬 불가
- 서버 추가/제거 시 ID 충돌 위험
- 데이터 센터 확장에 제약

**적합도**: 중소 규모 시스템에 적합하지만, 대규모 분산 환경에는 부적합

---

### 2. UUID (Universally Unique Identifier)

**개념**: 128비트의 고유 식별자를 생성하여 유일성을 보장합니다.

**장점**:

- 충돌 가능성 극히 낮음
- 서버 간 동기화 불필요
- 독립적인 ID 생성 가능

**단점**:

- 128비트로 길이가 김
- 시간 순 정렬 불가
- 숫자 기반 ID 요구사항 미충족

**적합도**: 유일성이 최우선이며, ID 길이나 정렬이 중요하지 않은 시스템에 적합

---

### 3. 티켓 서버 (Ticket Server)

**개념**: 중앙 집중형 서버에서 auto_increment를 사용하여 순차적인 ID를 생성합니다.

**장점**:

- 간단한 구현
- 순차적이고 숫자로 된 ID 생성 가능

**단점**:

- 단일 장애 지점(SPOF) 존재
- 확장성 및 고가용성 확보 어려움

**적합도**: 소규모 시스템이나 초기 단계의 프로젝트에 적합

---

### 4. 트위터 스노플레이크 (Twitter Snowflake)

**개념**: 64비트 ID를 다음과 같이 구성하여 유일성과 시간 순 정렬을 동시에 달성합니다.

- 1비트: 예약 비트
- 41비트: 타임스탬프 (밀리초 단위)
- 10비트: 머신 ID (데이터 센터 ID + 서버 ID)
- 12비트: 일련번호 (동일 밀리초 내에서의 구분자)

**장점**:

- 시간 순 정렬 가능
- 고성능 처리량 (초당 수십만 개 ID 생성 가능)
- 분산 환경에서 유일성 보장

**단점**:

- 구현 복잡성
- 시스템 시계 동기화 필요

**적합도**: 대규모 분산 시스템에 최적화된 솔루션


## 트위터 스노플레이크 상세 설계

**ID 구성**:

```

+-----------+----------------+------------+----------------------+
| 1비트 예약 | 41비트 타임스탬프 | 10비트 머신 ID | 12비트 일련번호 |
+-----------+----------------+------------+----------------------+
```

- **타임스탬프**: 기준 시점(epoch) 이후 경과한 밀리초 수
- **머신 ID**: 데이터 센터 ID와 서버 ID를 조합하여 고유한 머신 식별자 생성
- **일련번호**: 동일 밀리초 내에서 생성된 ID를 구분하기 위한 카운터

**운영 고려사항**:

- **시계 동기화**: NTP(Network Time Protocol)를 사용하여 서버 간 시계 동기화 필요
- **ID 충돌 방지**: 머신 ID와 일련번호를 적절히 관리하여 충돌 방지
- **확장성**: 머신 ID 비트 수 조정을 통해 데이터 센터 및 서버 수 확장 가능


## 결론

분산 시스템에서 유일한 ID를 생성하는 것은 시스템의 신뢰성과 확장성에 직결되는 중요한 과제입니다. 

다양한 방법 중에서 트위터 스노플레이크 방식은 유일성, 시간 순 정렬, 고성능 처리량 등 모든 요구사항을 만족시키며, 대규모 시스템에 가장 적합한 솔루션으로 평가됩니다. 

그러나 시스템의 규모와 요구사항에 따라 적절한 ID 생성 전략을 선택하는 것이 중요합니다.

추가자료

선착순 구매 프로젝트에서는 어떤 유일 ID를 생성하는가?

**어떤 ID**를 관리하고 있는지부터 생각해보자면

- **주문 번호** 생성
- **상품 ID** 생성
- **유저 ID** 생성
- **결제 기록 ID** 생성
- **배송 추적 ID** 생성
- **이벤트나 쿠폰 ID** 생성

특히 주문 번호, 결제 트랜잭션 ID 같은 건 **중복되면 치명적** 

(데이터 꼬임, 금액 중복 청구 같은 치명적인 오류가 발생할 수 있음.)

**Snowflake 방식**이 선착순 구매 프로젝트에서 쓸만할까?

- **유일성 보장**
    - 수평 확장(서버를 여러 대 늘리는 것)을 해도 중복되지 않는 ID를 생성할 수 있음
    - 예를 들어 여러 결제 서버가 있어도 **겹치지 않는 주문번호**를 생성할 수 있음
- **시간 순 정렬 가능**
    - 주문 기록이나 결제 기록을 **ID만 보고 시간 순 정렬**할 수 있음
    - createdAt 없이도 대략적인 생성 순서 파악이 가능.
- **고성능**
    - 초당 수만~수십만 개의 ID를 생성할 수 있음
    - 트래픽이 늘어나도 병목 없이 처리 가능.
- **독립적 생성 가능**
    - 데이터베이스에 매번 ID를 위해 질의할 필요 없이, 애플리케이션 레벨에서 생성 가능.
    - DB 락(lock)이나 **auto_increment** 때문에 병목이 생기는 걸 막을 수 있다.

## 구체적으로 어디에 적용할 수 있는지

| 적용 대상 | Snowflake ID 활용 |
| --- | --- |
| 주문(Order) | 주문 번호 생성 |
| 결제(Payment) | 결제 트랜잭션 ID 생성 |
| 사용자(User) | 회원가입 시 유저 고유 ID 생성 (숫자 기반으로 관리) |
| 상품(Product) | 상품 등록 시 상품 ID 생성 |
| 쿠폰(Coupon) | 쿠폰 발급 ID 관리 |
| 배송(Delivery) | 배송 요청/추적 ID 관리 |

### 적용 방법

- **직접 Snowflake 알고리즘을 구현해서 Java 서비스 레이어에서 사용하기**
    - Snowflake 구현체는 간단해서 직접 짤 수도 있음
    - `@Component`로 Bean 등록해서 쓰면 됨.
- **라이브러리 사용하기**
    - [Twitter Snowflake Java 버전](https://github.com/twitter-archive/snowflake) 오픈소스를 참고
    - Spring Boot용 ID 생성기 라이브러리 (예: [Leaf](https://github.com/Meituan-Dianping/Leaf)).

```jsx
public class SnowflakeIdGenerator {

    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    
    private final long epoch = 1609459200000L; // 2021-01-01 기준 시간
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > ~(-1L << workerIdBits) || workerId < 0) {
            throw new IllegalArgumentException("worker Id error");
        }
        if (datacenterId > ~(-1L << datacenterIdBits) || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id error");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - epoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
```

그리고 DI로 등록해서 서비스에서 호출할 때

```jsx
long orderId = snowflakeIdGenerator.nextId();
```

이렇게 호출하면 주문 번호가 만들어짐

## 주의할 점

- **서버 간 시계(Time) 동기화(NTP)** 반드시 해줘야 한함
    - 만약 서버 시간이 꼬이면, Snowflake ID가 꼬일 수 있음.
- **workerId/datacenterId를 어떻게 나눌지 정책 정하기**
    - 서버마다 설정해주거나, ZooKeeper 같은 걸로 동적으로 관리할 수도 있음

프로젝트에 Snowflake ID를 적용하면 **뭐가 좋아지나?**

## 데이터베이스 부하 감소 (병목 제거)

**기존 방식:**

- 주문 번호나 상품 ID를 만들 때 `DB auto_increment`(자동 증가 키)를 쓰면
    
    → 매번 DB에 **Insert/Select** 쿼리를 날려야 해.
    
- DB는 점점 트래픽이 몰리고, 충돌 방지하려고 락(lock)이 걸리기도 함.

**Snowflake 방식:**

- **서버 메모리 안**에서 바로 ID 생성.
- **DB에 의존 없이** 독립적으로 유일 ID 발급.
- 트래픽이 커져도 **DB 락 걸림 없음**, 병목 없음.

> 💡 요약: 서버 수가 늘어나도 DB 부하 없이 주문/결제 요청 처리가 매우 빨라진다.
> 

## 수평 확장성(Scale-Out) 확보

**기존 방식:**

- 하나의 DB에서만 ID를 생성하니까 서버 수를 늘려도 항상 **DB에 병목**이 생김.

**Snowflake 방식:**

- 서버를 수십~수백 대로 늘려도, 각 서버가 **스스로 고유 ID 생성** 가능.
- 분산 시스템에 **딱 맞는 구조**가 됨.

> 💡 요약: 서버가 10대, 100대가 돼도 서버마다 독립적으로 ID 발급 가능.
> 

## ID 자체가 "시간순 정렬"이 가능

Snowflake는 ID 안에 **타임스탬프 정보**가 들어있어.

- 주문 목록, 결제 기록 등을 `ORDER BY id DESC`만 해도 **최근 순 정렬**이 된다.
- 추가적인 `createdAt` 컬럼이 없어도 시간순 정렬이 가능할 수도 있다. (단, createdAt은 여전히 명시적으로 저장하는 게 안전하긴 함.)

> 💡 요약: 최근 주문 조회, 최신 결제 내역 같은 기능 구현이 쉬워진다.
> 

## 고유 ID 충돌 걱정 없음

- UUID(랜덤 ID)도 충돌은 거의 없지만, 완전히 배제할 순 없음.
- Snowflake는 **시간 + 서버 ID + 시퀀스 번호** 조합이라
    
    실질적으로 **충돌 가능성 0%에 가깝다**.
    

**특히 중요한 이유:**

- **주문 번호, 결제 ID**는 중복되면 진짜 최악이다. (잘못된 주문 처리, 이중 결제 등 발생)

> 💡 요약: 중복 없는 ID 덕분에 결제/주문 데이터 신뢰성이 크게 올라간다.
> 

## 빠른 ID 생성 속도

- 초당 **수십만~수백만 건**의 ID를 끊임없이 생성 가능.
- 즉, 극단적인 트래픽 폭증 상황 (예: 블랙프라이데이 세일, 이벤트)에도
    
    **ID 발급이 느려져서 에러나는 일**이 없게 된다.
    

> 💡 요약: 초대규모 세일/이벤트 상황에서도 ID 발급은 절대 병목이 안 생긴다.
>