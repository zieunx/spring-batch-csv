
# 대용량 CSV 데이터를 Spring Batch로 DB에 저장하기

## 설치하기

### 1. Git LFS 설치 및 Clone

대용량 CSV 파일을 관리를 위해 Git LFS가 필요합니다.


### 1-1. Git LFS 설치
- [Git LFS 공식 웹사이트](https://git-lfs.com/)에서 설치 파일 다운로드
1. MacOS
```bash
brew install git-lfs
```

### 1-2. 레포지토리 클론
Git LFS를 설치한 후 프로젝트를 클론하세요:

```bash
git clone https://github.com/zieunx/spring-batch-csv.git
```

### 2. Docker 설치

- [Docker 설치 가이드](https://docs.docker.com/get-docker/)를 참고하세요.

### 2-1. Docker Compose 실행

   ```bash
   cd docker
   docker-compose up
   ```

### 3. 애플리케이션 실행

   ```bash
   ./gradlew bootRun
   ```

---

## 프로젝트 주요 구조

- **`BatchJobRunner`**
    - Spring Boot 애플리케이션 실행 시 즉시 배치 Job을 실행하도록 구성된 Runner.

- **`RestaurantBatchJobConfig`**
    - 음식점 데이터를 담은 CSV 파일을 읽고, 데이터베이스에 저장하는 배치 Job을 정의.

- **`RestaurantInsertOnDuplicateKeyWriter`**
    - MySQL의 `INSERT ON DUPLICATE KEY UPDATE` 문법을 활용하여 데이터베이스에 중복 삽입을 방지하며 업데이트 처리.

---

## 구현 방향

- CSV 데이터 중 '관리번호(`managementNumber`)'를 데이터베이스에서 `UNIQUE KEY`로 사용
- Reader:
  - CSV 데이터를 읽고 관리번호(`managementNumber`) 기준으로 중복 제거.
  - 각 라인을 RestaurantCsvRowData 객체로 변환.
- Processor:
  - RestaurantCsvRowData를 Restaurant 엔티티로 변환.
- Writer:
  - MySQL의 ON DUPLICATE KEY UPDATE 문법을 활용
    - 관리번호가 이미 존재하면 데이터를 업데이트.
    - 관리번호가 없으면 새로운 데이터를 삽입.

---

## 한계 및 향후 개선 방향

1. **처리 시간 줄이기**
    - 파티셔닝(`partitioning`)을 통해 병렬 처리 속도를 개선하려 했으나, 일부 기술적 이슈로 실패([RestaurantPartitionBatchJobConfig](src/main/java/com/danal/publicdataprocessor/batch/job/RestaurantPartitionBatchJobConfig.java)).

2. **Spring Batch 관리 도구**
    - Jenkins, Spring Cloud Data Flow 등의 관리 도구 추가 고민.

4. **성능 최적화**
    - 파티셔닝 재도전을 통해 데이터 처리 속도를 대폭 개선.

5. **실패 복구 방법**
    - Spring Batch에서 제공하는 재시도(`retry`) 및 실패 처리(`skip`) 기능을 활용하여 처리 안정성을 높임.

6. **테스트 강화**
    - 단위 테스트, 통합 테스트 등 코드 레벨에서 안정성을 확인할 수 있도록 테스트 작성.

---

## DDL

- [DDL SQL File (click)](./db/ddl.sql)
