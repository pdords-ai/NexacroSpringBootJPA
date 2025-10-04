# Java와 Maven 자동 설치 스크립트
# 관리자 권한으로 실행해야 합니다

Write-Host "🚀 Java와 Maven 설치를 시작합니다..." -ForegroundColor Green

# 설치 디렉토리 설정
$installDir = "C:\Development"
$javaDir = "$installDir\Java"
$mavenDir = "$installDir\Maven"

# 디렉토리 생성
if (!(Test-Path $installDir)) {
    New-Item -ItemType Directory -Path $installDir -Force
    Write-Host "✅ 설치 디렉토리 생성: $installDir" -ForegroundColor Yellow
}

# Java 17 다운로드 및 설치
Write-Host "📥 Java 17 다운로드 중..." -ForegroundColor Cyan
$javaUrl = "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_windows-x64_bin.zip"
$javaZip = "$installDir\openjdk-17.0.2_windows-x64_bin.zip"

try {
    Invoke-WebRequest -Uri $javaUrl -OutFile $javaZip -UseBasicParsing
    Write-Host "✅ Java 17 다운로드 완료" -ForegroundColor Green
    
    # Java 압축 해제
    Write-Host "📦 Java 압축 해제 중..." -ForegroundColor Cyan
    Expand-Archive -Path $javaZip -DestinationPath $javaDir -Force
    $javaHome = Get-ChildItem -Path $javaDir -Directory | Where-Object { $_.Name -like "jdk-*" } | Select-Object -First 1
    $javaHomePath = $javaHome.FullName
    
    Write-Host "✅ Java 설치 완료: $javaHomePath" -ForegroundColor Green
} catch {
    Write-Host "❌ Java 다운로드 실패: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "💡 수동 설치를 위해 다음 링크를 방문하세요: https://adoptium.net/temurin/releases/?version=17" -ForegroundColor Yellow
}

# Maven 다운로드 및 설치
Write-Host "📥 Maven 다운로드 중..." -ForegroundColor Cyan
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
$mavenZip = "$installDir\apache-maven-3.9.6-bin.zip"

try {
    Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip -UseBasicParsing
    Write-Host "✅ Maven 다운로드 완료" -ForegroundColor Green
    
    # Maven 압축 해제
    Write-Host "📦 Maven 압축 해제 중..." -ForegroundColor Cyan
    Expand-Archive -Path $mavenZip -DestinationPath $mavenDir -Force
    $mavenHomePath = "$mavenDir\apache-maven-3.9.6"
    
    Write-Host "✅ Maven 설치 완료: $mavenHomePath" -ForegroundColor Green
} catch {
    Write-Host "❌ Maven 다운로드 실패: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "💡 수동 설치를 위해 다음 링크를 방문하세요: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
}

# 환경변수 설정
Write-Host "🔧 환경변수 설정 중..." -ForegroundColor Cyan

try {
    # JAVA_HOME 설정
    if ($javaHomePath) {
        [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHomePath, "Machine")
        Write-Host "✅ JAVA_HOME 설정: $javaHomePath" -ForegroundColor Green
    }
    
    # MAVEN_HOME 설정
    if ($mavenHomePath) {
        [Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHomePath, "Machine")
        Write-Host "✅ MAVEN_HOME 설정: $mavenHomePath" -ForegroundColor Green
    }
    
    # PATH에 Java와 Maven 추가
    $currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")
    $newPath = $currentPath
    
    if ($javaHomePath -and $currentPath -notlike "*$javaHomePath\bin*") {
        $newPath += ";$javaHomePath\bin"
    }
    
    if ($mavenHomePath -and $currentPath -notlike "*$mavenHomePath\bin*") {
        $newPath += ";$mavenHomePath\bin"
    }
    
    [Environment]::SetEnvironmentVariable("PATH", $newPath, "Machine")
    Write-Host "✅ PATH 환경변수 업데이트 완료" -ForegroundColor Green
    
} catch {
    Write-Host "❌ 환경변수 설정 실패: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "💡 수동으로 환경변수를 설정해야 합니다." -ForegroundColor Yellow
}

# 임시 파일 정리
Write-Host "🧹 임시 파일 정리 중..." -ForegroundColor Cyan
if (Test-Path $javaZip) { Remove-Item $javaZip -Force }
if (Test-Path $mavenZip) { Remove-Item $mavenZip -Force }

Write-Host "🎉 설치 완료!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 다음 단계:" -ForegroundColor Yellow
Write-Host "1. PowerShell을 재시작하세요" -ForegroundColor White
Write-Host "2. java -version 명령어로 Java 설치 확인" -ForegroundColor White
Write-Host "3. mvn -version 명령어로 Maven 설치 확인" -ForegroundColor White
Write-Host "4. cd d:\workspace\Nexacro\nexacro-spring-backend" -ForegroundColor White
Write-Host "5. mvn spring-boot:run" -ForegroundColor White
Write-Host ""
Write-Host "💡 만약 환경변수가 적용되지 않으면 컴퓨터를 재시작하세요." -ForegroundColor Yellow
