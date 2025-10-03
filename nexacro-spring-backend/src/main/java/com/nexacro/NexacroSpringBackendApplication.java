package com.nexacro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 메인 애플리케이션 클래스
 * 
 * 이 클래스는 Spring Boot 애플리케이션의 시작점입니다.
 * @SpringBootApplication 어노테이션이 다음 기능들을 자동으로 설정합니다:
 * - @Configuration: 설정 클래스임을 나타냄
 * - @EnableAutoConfiguration: 자동 설정 활성화
 * - @ComponentScan: 컴포넌트 스캔 활성화
 */
@SpringBootApplication
public class NexacroSpringBackendApplication {

    /**
     * 메인 메서드 - 애플리케이션 시작점
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        // Spring Boot 애플리케이션을 시작합니다
        SpringApplication.run(NexacroSpringBackendApplication.class, args);
        
        // 애플리케이션 시작 후 콘솔에 메시지 출력
        System.out.println("==========================================");
        System.out.println("🚀 Nexacro Spring Backend 서버가 시작되었습니다!");
        System.out.println("📊 API 엔드포인트:");
        System.out.println("   - GET  /api/sales - 판매 데이터 조회");
        System.out.println("   - POST /api/sales - 판매 데이터 생성");
        System.out.println("   - PUT  /api/sales/{id} - 판매 데이터 수정");
        System.out.println("   - DELETE /api/sales/{id} - 판매 데이터 삭제");
        System.out.println("🌐 서버 주소: http://localhost:8080");
        System.out.println("🗄️  H2 콘솔: http://localhost:8080/h2-console");
        System.out.println("==========================================");
    }
}
