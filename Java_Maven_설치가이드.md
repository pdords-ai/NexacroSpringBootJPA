# 🚀 Java와 Maven 설치 가이드

## 방법 1: 자동 설치 (관리자 권한 필요)

### 1단계: PowerShell을 관리자 권한으로 실행
1. Windows 키 + X 누르기
2. "Windows PowerShell (관리자)" 선택
3. "예" 클릭

### 2단계: 설치 스크립트 실행
```powershell
cd d:\workspace\Nexacro
.\install_java_maven.ps1
```

## 방법 2: 수동 설치 (권장)

### Java 17 설치

1. **다운로드**:
   - https://adoptium.net/temurin/releases/?version=17 방문
   - "Windows x64" 버전 다운로드 (JDK 17.0.x)

2. **설치**:
   - 다운로드한 .msi 파일 실행
   - 기본 설정으로 설치 진행
   - 설치 경로: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot\`

3. **환경변수 설정**:
   - Windows 키 + R → `sysdm.cpl` 입력 → Enter
   - "고급" 탭 → "환경 변수" 클릭
   - "시스템 변수"에서 "새로 만들기" 클릭
     - 변수 이름: `JAVA_HOME`
     - 변수 값: `C:\Program Files\Eclipse Adoptium\jdk-17.0.9.9-hotspot`
   - "시스템 변수"에서 "Path" 선택 → "편집" 클릭
   - "새로 만들기" 클릭 → `%JAVA_HOME%\bin` 추가

### Maven 설치

1. **다운로드**:
   - https://maven.apache.org/download.cgi 방문
   - "Binary zip archive" 다운로드 (apache-maven-3.9.6-bin.zip)

2. **설치**:
   - `C:\Program Files\Apache\Maven\` 폴더 생성
   - 다운로드한 zip 파일을 해당 폴더에 압축 해제
   - 최종 경로: `C:\Program Files\Apache\Maven\apache-maven-3.9.6\`

3. **환경변수 설정**:
   - "시스템 변수"에서 "새로 만들기" 클릭
     - 변수 이름: `MAVEN_HOME`
     - 변수 값: `C:\Program Files\Apache\Maven\apache-maven-3.9.6`
   - "시스템 변수"에서 "Path" 선택 → "편집" 클릭
   - "새로 만들기" 클릭 → `%MAVEN_HOME%\bin` 추가

## 설치 확인

새로운 PowerShell 창을 열고 다음 명령어 실행:

```powershell
java -version
mvn -version
```

정상적으로 설치되었다면 다음과 같은 출력이 나와야 합니다:

```
java version "17.0.9" 2023-10-17 LTS
Java(TM) SE Runtime Environment (build 17.0.9+9-LTS-211)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.9+9-LTS-211, mixed mode, sharing)

Apache Maven 3.9.6 (924fd5b40f0a0f205421d3c13c384a442c9a9422)
Maven home: C:\Program Files\Apache\Maven\apache-maven-3.9.6
Java version: 17.0.9, vendor: Eclipse Adoptium, runtime: C:\Program Files\Eclipse Adoptium\jdk-17.0.9.9-hotspot
```

## Spring Boot 애플리케이션 실행

설치가 완료되면 다음 명령어로 애플리케이션을 실행할 수 있습니다:

```powershell
cd d:\workspace\Nexacro\nexacro-spring-backend
mvn spring-boot:run
```

## 문제 해결

### 환경변수가 적용되지 않는 경우
1. PowerShell을 완전히 종료
2. 새 PowerShell 창 열기
3. 그래도 안 되면 컴퓨터 재시작

### Java 버전이 다른 경우
- Java 11 이상이면 Spring Boot 3.x가 실행됩니다
- Java 8을 사용하려면 Spring Boot 2.x로 다운그레이드 필요

### Maven이 인식되지 않는 경우
- 환경변수 PATH에 `%MAVEN_HOME%\bin`이 정확히 추가되었는지 확인
- PowerShell을 재시작

## 🎯 설치 완료 후 할 일

1. **백엔드 실행**:
   ```powershell
   cd d:\workspace\Nexacro\nexacro-spring-backend
   mvn spring-boot:run
   ```

2. **프론트엔드 실행**:
   - `d:\workspace\Nexacro\01_기본_UI_컴포넌트\16_인사정보관리.html` 파일을 브라우저에서 열기

3. **확인**:
   - http://localhost:8080/api/employees 에서 API 응답 확인
   - 넥사크로 페이지에서 직원 목록이 표시되는지 확인

---

**Happy Coding! 🎉**
