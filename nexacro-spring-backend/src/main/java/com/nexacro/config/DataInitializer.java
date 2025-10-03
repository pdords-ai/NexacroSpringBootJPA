package com.nexacro.config;

import com.nexacro.entity.SalesData;
import com.nexacro.entity.User;
import com.nexacro.repository.SalesDataRepository;
import com.nexacro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 데이터 초기화 클래스
 * 
 * 애플리케이션 시작 시 샘플 데이터를 자동으로 생성합니다.
 * CommandLineRunner를 구현하여 Spring Boot 시작 후 실행됩니다.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final SalesDataRepository salesDataRepository;
    private final UserRepository userRepository;
    private final Random random = new Random();

    @Autowired
    public DataInitializer(SalesDataRepository salesDataRepository, UserRepository userRepository) {
        this.salesDataRepository = salesDataRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 이미 존재하는지 확인
        if (salesDataRepository.count() == 0) {
            initializeSalesData();
        }
        
        if (userRepository.count() == 0) {
            initializeUserData();
        }
        
        System.out.println("✅ 샘플 데이터 초기화가 완료되었습니다!");
        System.out.println("📊 판매 데이터: " + salesDataRepository.count() + "건");
        System.out.println("👥 사용자 데이터: " + userRepository.count() + "건");
    }

    /**
     * 판매 데이터 초기화
     */
    private void initializeSalesData() {
        List<String> productNames = Arrays.asList(
            "스마트폰", "노트북", "태블릿", "이어폰", "스마트워치",
            "게이밍 마우스", "키보드", "모니터", "웹캠", "스피커",
            "무선 충전기", "케이스", "보호필름", "충전 케이블", "어댑터"
        );

        List<String> categories = Arrays.asList(
            "전자제품", "컴퓨터", "음향기기", "액세서리", "모바일"
        );

        List<String> salespersons = Arrays.asList(
            "김철수", "이영희", "박민수", "정수진", "최동현",
            "한미영", "강태호", "윤서연", "임재현", "송지은"
        );

        List<String> regions = Arrays.asList(
            "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종"
        );

        List<String> statuses = Arrays.asList(
            "완료", "진행중", "대기", "취소"
        );

        // 100개의 샘플 판매 데이터 생성
        for (int i = 0; i < 100; i++) {
            String productName = productNames.get(random.nextInt(productNames.size()));
            String category = categories.get(random.nextInt(categories.size()));
            int price = 10000 + random.nextInt(900000); // 10,000 ~ 900,000원
            int quantity = 1 + random.nextInt(10); // 1 ~ 10개
            LocalDate salesDate = LocalDate.now().minusDays(random.nextInt(365)); // 최근 1년
            String salesperson = salespersons.get(random.nextInt(salespersons.size()));
            String region = regions.get(random.nextInt(regions.size()));
            String status = statuses.get(random.nextInt(statuses.size()));

            SalesData salesData = new SalesData(
                productName, category, price, quantity, 
                salesDate, salesperson, region, status
            );

            salesDataRepository.save(salesData);
        }
    }

    /**
     * 사용자 데이터 초기화
     */
    private void initializeUserData() {
        List<String> firstNames = Arrays.asList(
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
            "한", "오", "서", "신", "권", "황", "안", "송", "전", "고"
        );

        List<String> lastNames = Arrays.asList(
            "철수", "영희", "민수", "수진", "동현", "미영", "태호", "서연", "재현", "지은",
            "현우", "지영", "준호", "예진", "성민", "하은", "우진", "서현", "민준", "지우"
        );

        List<String> genders = Arrays.asList("남성", "여성");

        // 50개의 샘플 사용자 데이터 생성
        for (int i = 0; i < 50; i++) {
            String name = firstNames.get(random.nextInt(firstNames.size())) + 
                         lastNames.get(random.nextInt(lastNames.size()));
            String email = "user" + (i + 1) + "@example.com";
            String phone = "010-" + String.format("%04d", random.nextInt(10000)) + "-" + 
                          String.format("%04d", random.nextInt(10000));
            int age = 20 + random.nextInt(50); // 20 ~ 69세
            String gender = genders.get(random.nextInt(genders.size()));

            User user = new User(name, email, phone, age, gender);
            userRepository.save(user);
        }
    }
}
