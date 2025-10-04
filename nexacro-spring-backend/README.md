# Nexacro Spring Boot Backend

넥사크로 프론트엔드와 연동되는 Spring Boot 백엔드 애플리케이션입니다.

## 🚀 주요 기능

### 1. 사용자 관리 (User Management)
- 사용자 CRUD 작업
- 이메일 중복 검사
- 나이대별, 성별별 통계
- 복합 조건 검색

### 2. 판매 데이터 관리 (Sales Data Management)
- 판매 데이터 CRUD 작업
- 제품별, 지역별, 판매원별 분석
- 날짜 범위별 조회
- 판매 통계 정보

### 3. 인사정보 관리 (Employee Management) ⭐ **NEW**
- 직원 정보 CRUD 작업
- 부서별, 직급별, 재직상태별 관리
- 급여 통계 및 근속년수 분석
- 퇴사/복직 처리
- 긴급연락처 관리

## 🏗️ 프로젝트 구조

```
src/main/java/com/nexacro/
├── config/
│   └── DataInitializer.java          # 초기 데이터 설정
├── controller/
│   ├── UserController.java           # 사용자 REST API
│   ├── SalesDataController.java      # 판매 데이터 REST API
│   └── EmployeeController.java       # 직원 REST API ⭐
├── entity/
│   ├── User.java                     # 사용자 엔티티
│   ├── SalesData.java                # 판매 데이터 엔티티
│   └── Employee.java                 # 직원 엔티티 ⭐
├── repository/
│   ├── UserRepository.java           # 사용자 데이터 접근
│   ├── SalesDataRepository.java      # 판매 데이터 접근
│   └── EmployeeRepository.java       # 직원 데이터 접근 ⭐
├── service/
│   ├── UserService.java              # 사용자 비즈니스 로직
│   ├── SalesDataService.java         # 판매 데이터 비즈니스 로직
│   └── EmployeeService.java          # 직원 비즈니스 로직 ⭐
└── NexacroSpringBackendApplication.java  # 메인 애플리케이션
```

## 🗄️ 데이터베이스 스키마

### Users 테이블
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    age INTEGER,
    gender VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

### Sales Data 테이블
```sql
CREATE TABLE sales_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    sales_date DATE NOT NULL,
    salesperson VARCHAR(50) NOT NULL,
    region VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL
);
```

### Employees 테이블 ⭐ **NEW**
```sql
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    ssn VARCHAR(20) NOT NULL,
    department VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL,
    resignation_date DATE,
    salary BIGINT NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(200),
    emergency_contact VARCHAR(20),
    emergency_relation VARCHAR(20),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

## 🔌 API 엔드포인트

### 사용자 관리 API
- `GET /api/users` - 전체 사용자 조회
- `GET /api/users/{id}` - 특정 사용자 조회
- `POST /api/users` - 새 사용자 등록
- `PUT /api/users/{id}` - 사용자 정보 수정
- `DELETE /api/users/{id}` - 사용자 삭제
- `GET /api/users/search?name={name}` - 이름으로 검색
- `GET /api/users/filter` - 복합 조건 검색
- `GET /api/users/statistics/overall` - 전체 통계

### 판매 데이터 API
- `GET /api/sales` - 전체 판매 데이터 조회
- `GET /api/sales/{id}` - 특정 판매 데이터 조회
- `POST /api/sales` - 새 판매 데이터 등록
- `PUT /api/sales/{id}` - 판매 데이터 수정
- `DELETE /api/sales/{id}` - 판매 데이터 삭제
- `GET /api/sales/statistics/overall` - 판매 통계

### 직원 관리 API ⭐ **NEW**
- `GET /api/employees` - 전체 직원 조회
- `GET /api/employees/{id}` - 특정 직원 조회
- `GET /api/employees/employee-number/{employeeNumber}` - 사번으로 조회
- `POST /api/employees` - 새 직원 등록
- `PUT /api/employees/{id}` - 직원 정보 수정
- `DELETE /api/employees/{id}` - 직원 삭제
- `GET /api/employees/filter` - 복합 조건 검색
- `GET /api/employees/department/{department}` - 부서별 조회
- `GET /api/employees/position/{position}` - 직급별 조회
- `GET /api/employees/status/{status}` - 재직상태별 조회
- `GET /api/employees/salary-range` - 급여 범위별 조회
- `GET /api/employees/hire-date-range` - 입사일 범위별 조회
- `PUT /api/employees/{id}/resign` - 퇴사 처리
- `PUT /api/employees/{id}/rehire` - 복직 처리
- `GET /api/employees/statistics/department` - 부서별 통계
- `GET /api/employees/statistics/position` - 직급별 통계
- `GET /api/employees/statistics/status` - 재직상태별 통계
- `GET /api/employees/statistics/salary` - 급여 통계
- `GET /api/employees/statistics/tenure` - 근속년수별 통계
- `GET /api/employees/statistics/overall` - 전체 통계

## 🚀 실행 방법

### 1. 프로젝트 빌드
```bash
mvn clean install
```

### 2. 애플리케이션 실행
```bash
mvn spring-boot:run
```

### 3. 애플리케이션 접속
- **API 서버**: http://localhost:8080
- **H2 데이터베이스 콘솔**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (비어있음)

## 📊 초기 데이터

애플리케이션 시작 시 자동으로 다음 샘플 데이터가 생성됩니다:

- **사용자 데이터**: 50명의 샘플 사용자
- **판매 데이터**: 100건의 샘플 판매 데이터
- **직원 데이터**: 30명의 샘플 직원 ⭐ **NEW**

## 🛠️ 기술 스택

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: 데이터 접근 계층
- **H2 Database**: 인메모리 데이터베이스
- **Maven**: 빌드 도구
- **Jakarta Validation**: 데이터 검증

## 🔧 설정 파일

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080
```

## 🧪 테스트

### API 테스트 예제

#### 직원 등록
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "employeeNumber": "EMP0001",
    "name": "홍길동",
    "ssn": "123456-1234567",
    "department": "개발팀",
    "position": "사원",
    "hireDate": "2024-01-01",
    "salary": 40000000,
    "email": "hong@company.com",
    "phone": "010-1234-5678",
    "address": "서울시 강남구",
    "emergencyContact": "010-9876-5432",
    "emergencyRelation": "부",
    "status": "재직"
  }'
```

#### 직원 목록 조회
```bash
curl http://localhost:8080/api/employees
```

#### 부서별 직원 조회
```bash
curl http://localhost:8080/api/employees/department/개발팀
```

## 🔍 주요 기능 상세

### 인사정보 관리 시스템

#### 1. 직원 정보 관리
- **사번 관리**: 고유한 사번으로 직원 식별
- **개인정보**: 이름, 주민등록번호, 연락처, 주소
- **업무정보**: 부서, 직급, 입사일, 급여
- **긴급연락처**: 비상시 연락 가능한 정보

#### 2. 재직상태 관리
- **재직**: 현재 근무 중인 직원
- **휴직**: 일시적으로 근무를 중단한 직원
- **퇴직**: 회사를 떠난 직원 (퇴사일 기록)

#### 3. 통계 및 분석
- **부서별 인원 현황**
- **직급별 분포**
- **급여 통계** (평균, 최고, 최저)
- **근속년수별 분포**
- **재직상태별 현황**

#### 4. 검색 및 필터링
- **이름 검색**: 부분 일치 검색
- **부서별 필터링**
- **직급별 필터링**
- **재직상태별 필터링**
- **급여 범위별 검색**
- **입사일 범위별 검색**

## 🎯 넥사크로 연동

이 백엔드는 넥사크로 프론트엔드와 완벽하게 연동됩니다:

1. **CORS 설정**: 모든 도메인에서 API 접근 허용
2. **RESTful API**: 표준 HTTP 메서드 사용
3. **JSON 응답**: 넥사크로에서 쉽게 파싱 가능한 JSON 형식
4. **에러 처리**: 적절한 HTTP 상태 코드 반환

## 📝 라이선스

이 프로젝트는 학습 목적으로 자유롭게 사용할 수 있습니다.

---

**Happy Coding! 🎉**

Spring Boot와 넥사크로로 멋진 애플리케이션을 만들어보세요!
