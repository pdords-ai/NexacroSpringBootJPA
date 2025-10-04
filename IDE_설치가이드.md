# 🚀 IDE를 사용한 간단한 실행 방법

## IntelliJ IDEA Community Edition 설치 (무료)

### 1단계: IntelliJ IDEA 다운로드
1. https://www.jetbrains.com/idea/download/ 방문
2. "Community" 버전 다운로드 (무료)
3. 설치 파일 실행하여 설치

### 2단계: 프로젝트 열기
1. IntelliJ IDEA 실행
2. "Open" 클릭
3. `d:\workspace\Nexacro\nexacro-spring-backend` 폴더 선택
4. "OK" 클릭

### 3단계: JDK 설정
1. File → Project Structure → Project
2. Project SDK에서 "Download JDK" 클릭
3. Version: 17, Vendor: Eclipse Temurin 선택
4. "Download" 클릭

### 4단계: 애플리케이션 실행
1. `src/main/java/com/nexacro/NexacroSpringBackendApplication.java` 파일 열기
2. `main` 메서드 옆의 녹색 화살표 클릭
3. 또는 Shift + F10 키 누르기

## Eclipse 사용 방법

### 1단계: Eclipse 설치
1. https://www.eclipse.org/downloads/ 방문
2. "Eclipse IDE for Enterprise Java and Web Developers" 다운로드
3. 설치 파일 실행

### 2단계: 프로젝트 가져오기
1. File → Import → Existing Maven Projects
2. `d:\workspace\Nexacro\nexacro-spring-backend` 선택
3. Finish 클릭

### 3단계: 실행
1. 프로젝트 우클릭 → Run As → Java Application
2. `NexacroSpringBackendApplication` 선택

## VS Code 사용 방법

### 1단계: VS Code 확장 설치
1. VS Code 실행
2. Extensions에서 다음 확장 설치:
   - Extension Pack for Java
   - Spring Boot Extension Pack

### 2단계: 프로젝트 열기
1. File → Open Folder
2. `d:\workspace\Nexacro\nexacro-spring-backend` 선택

### 3단계: 실행
1. `NexacroSpringBackendApplication.java` 파일 열기
2. `main` 메서드 위의 "Run" 버튼 클릭

## 🎯 실행 확인

애플리케이션이 성공적으로 실행되면:

1. **콘솔에 다음과 같은 메시지가 표시됩니다**:
   ```
   Started NexacroSpringBackendApplication in X.XXX seconds
   ```

2. **브라우저에서 API 테스트**:
   - http://localhost:8080/api/employees
   - JSON 형태의 직원 데이터가 표시되어야 합니다

3. **넥사크로 페이지 실행**:
   - `d:\workspace\Nexacro\01_기본_UI_컴포넌트\16_인사정보관리.html` 파일을 브라우저에서 열기
   - 직원 목록이 그리드에 표시되어야 합니다

## 🔧 문제 해결

### 포트 8080이 이미 사용 중인 경우
- application.yml에서 `server.port: 8081`로 변경
- 넥사크로 페이지의 API URL도 `localhost:8081`로 변경

### Java 버전 문제
- IDE에서 Project SDK를 Java 17로 설정
- 또는 Java 11 이상이면 실행 가능

### Maven 의존성 문제
- IDE에서 Maven 프로젝트 새로고침
- 또는 `mvn clean install` 실행 (IDE 터미널에서)

---

**IDE를 사용하면 Java와 Maven을 수동으로 설치할 필요가 없습니다!** 🎉
