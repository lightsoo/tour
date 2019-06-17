# 개발 프레임워크
- Java version: 1.8
- Spring Boot: 2.0.5.RELEASE

# 문제해결 전략
## 지역코드 설계
- 한국관광공사의 Open API를 사용하여 광역지방자치단체, 기초지방자치단체의 지역코드를 조회하고 지자체의 계층구조에 따라 코드 설계
- RegionCode에서 지역코드를 관리하고 Bean 초기화 시점에서 모든 지역코드를 Repository에 저장

## DB Schema 조건
- 제약조건:  _**서비스 지역 코드**_ 컬럼을 PK로 추가하여 Entity 디자인
- CSV의 서비스 지역컬럼을 보고 1개 이상의 서비스지역이 가능하다고 유추
  - ex) 강원도 속초, 양양, 고성 
- 프로그램과 서비스지역은 다대다 관계의 엔티티 설계

## DB Schema
#### Program: 프로그램
| 컬럼  | 타입| 설명 | 
| :---- | :----- |:-----  |
| id(PK) | integer | 자동생성번호|
| name | String | 프로그램명 |
| intro | String | 프로그램 소개|
| theme | String | 테마별 분류|
| service_region_name | String | 서비스지역|
| description | String | 프로그램 상세소개|

#### ServiceRegion: 서비스지역

| 컬럼  | 타입| 설명 | 
| :---- | :----- |:-----  |
| code(PK) | integer | 자치단체 코드|
| sido_name | String | 광역지방자치단체 이름|
| gugun_name | String | 기초지방자치단체 이름|

#### ProgramServiceRegion: 프로그램서비스지역

| 컬럼  | 타입| 설명 | 
| :---- | :----- |:-----  |
| program_id(PK) | integer | 복합키|
| service_region_code(PK) | integer |복합키 |

# 빌드 및 실행방법
## IntelliJ IDE Lombok plugin 설정
 * `Preferences -> Build, Excution, Deployment -> Compiler -> Anottation Processors -> Enable annotation processing`

## 패키징
```bash
./gradlew clean bootJar
```

## 실행
```bash
java -jar build/libs/tour-0.0.1-SNAPSHOT.jar
```
## API SPEC
- http://127.0.0.1:8080/swagger-ui.html#

## 기본문제
- [x] 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API를 개발하세요.
- [ ] 2. 생태 관광정보 데이터를 조회/추가/수정할 수 있는 API를 개발하세요
  - 단, 조회는 서비스 지역 코드를 기준으로 검색합니다.
- [x] 3. 생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 출력하는 API 를 개발하세요.
  - 예를들어, “평창군”이라는 문자열을 입력 받으면 아래와 같은 결과를 출력합니다.
  - 단, 출력 결과에 지역은 지역 코드를 출력합니다.
- [ ] 4. 생태 정보 데이터에 "프로그램 소개” 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 출력하는 API 를 개발하세요.
  - 예를 들어, “세계문화유산” 문자열을 입력 받아 포함된 레코드에서 서비스 지역 개수와 지역정보를 출력
- [ ] 5. 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 출력 하는 API 를 개발하세요

## note
- 처음 서비스지역와 관광 프로그램를 일대다 관계로 로직을 구현하는 동안 로직이 복잡해지고 원활하게 구현이 안되면서 DB스키마를 잘못 설계했다고 판단했습니다.
- 다대다 구조로 변경하여 진행하면서 기본문제를 많이 풀지 못했습니다.
