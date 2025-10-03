package com.nexacro.controller;

import com.nexacro.entity.SalesData;
import com.nexacro.service.SalesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 판매 데이터 REST API 컨트롤러
 * 
 * Nexacro 그리드에서 사용할 수 있는 REST API 엔드포인트를 제공합니다.
 * CORS 설정을 통해 Nexacro 애플리케이션에서 접근할 수 있습니다.
 */
@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*") // CORS 설정 - 모든 도메인에서 접근 허용
public class SalesDataController {

    private final SalesDataService salesDataService;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param salesDataService 판매 데이터 서비스
     */
    @Autowired
    public SalesDataController(SalesDataService salesDataService) {
        this.salesDataService = salesDataService;
    }

    /**
     * 모든 판매 데이터를 조회하는 API
     * 
     * @return 모든 판매 데이터 목록
     */
    @GetMapping
    public ResponseEntity<List<SalesData>> getAllSalesData() {
        try {
            List<SalesData> salesDataList = salesDataService.getAllSalesData();
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ID로 판매 데이터를 조회하는 API
     * 
     * @param id 조회할 판매 데이터 ID
     * @return 해당 ID의 판매 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesData> getSalesDataById(@PathVariable Long id) {
        try {
            Optional<SalesData> salesData = salesDataService.getSalesDataById(id);
            return salesData.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 새로운 판매 데이터를 생성하는 API
     * 
     * @param salesData 생성할 판매 데이터
     * @return 생성된 판매 데이터
     */
    @PostMapping
    public ResponseEntity<SalesData> createSalesData(@Valid @RequestBody SalesData salesData) {
        try {
            SalesData createdSalesData = salesDataService.saveSalesData(salesData);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSalesData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 판매 데이터를 업데이트하는 API
     * 
     * @param id 업데이트할 판매 데이터 ID
     * @param salesData 업데이트할 데이터
     * @return 업데이트된 판매 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesData> updateSalesData(@PathVariable Long id, 
                                                   @Valid @RequestBody SalesData salesData) {
        try {
            SalesData updatedSalesData = salesDataService.updateSalesData(id, salesData);
            return ResponseEntity.ok(updatedSalesData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 판매 데이터를 삭제하는 API
     * 
     * @param id 삭제할 판매 데이터 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSalesData(@PathVariable Long id) {
        try {
            salesDataService.deleteSalesData(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "판매 데이터가 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 카테고리별로 판매 데이터를 조회하는 API
     * 
     * @param category 조회할 카테고리
     * @return 해당 카테고리의 판매 데이터 목록
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SalesData>> getSalesDataByCategory(@PathVariable String category) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataByCategory(category);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 지역별로 판매 데이터를 조회하는 API
     * 
     * @param region 조회할 지역
     * @return 해당 지역의 판매 데이터 목록
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<List<SalesData>> getSalesDataByRegion(@PathVariable String region) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataByRegion(region);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 판매원별로 판매 데이터를 조회하는 API
     * 
     * @param salesperson 조회할 판매원
     * @return 해당 판매원의 판매 데이터 목록
     */
    @GetMapping("/salesperson/{salesperson}")
    public ResponseEntity<List<SalesData>> getSalesDataBySalesperson(@PathVariable String salesperson) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataBySalesperson(salesperson);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 상태별로 판매 데이터를 조회하는 API
     * 
     * @param status 조회할 상태
     * @return 해당 상태의 판매 데이터 목록
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SalesData>> getSalesDataByStatus(@PathVariable String status) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataByStatus(status);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 특정 기간의 판매 데이터를 조회하는 API
     * 
     * @param startDate 시작 날짜 (YYYY-MM-DD 형식)
     * @param endDate 종료 날짜 (YYYY-MM-DD 형식)
     * @return 해당 기간의 판매 데이터 목록
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<SalesData>> getSalesDataByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<SalesData> salesDataList = salesDataService.getSalesDataByDateRange(start, end);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 제품명으로 판매 데이터를 검색하는 API
     * 
     * @param productName 검색할 제품명 키워드
     * @return 해당 키워드가 포함된 판매 데이터 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<SalesData>> searchSalesDataByProductName(
            @RequestParam String productName) {
        try {
            List<SalesData> salesDataList = salesDataService.searchSalesDataByProductName(productName);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 가격 범위로 판매 데이터를 조회하는 API
     * 
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 해당 가격 범위의 판매 데이터 목록
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<SalesData>> getSalesDataByPriceRange(
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 복합 조건으로 판매 데이터를 조회하는 API
     * 
     * @param category 카테고리 (선택사항)
     * @param region 지역 (선택사항)
     * @param status 상태 (선택사항)
     * @return 검색 조건에 맞는 판매 데이터 목록
     */
    @GetMapping("/filter")
    public ResponseEntity<List<SalesData>> getSalesDataWithFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String status) {
        try {
            List<SalesData> salesDataList = salesDataService.getSalesDataWithFilters(category, region, status);
            return ResponseEntity.ok(salesDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 카테고리별 총 매출을 조회하는 API
     * 
     * @return 카테고리별 총 매출 정보
     */
    @GetMapping("/statistics/category")
    public ResponseEntity<List<Object[]>> getTotalSalesByCategory() {
        try {
            List<Object[]> statistics = salesDataService.getTotalSalesByCategory();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 지역별 총 매출을 조회하는 API
     * 
     * @return 지역별 총 매출 정보
     */
    @GetMapping("/statistics/region")
    public ResponseEntity<List<Object[]>> getTotalSalesByRegion() {
        try {
            List<Object[]> statistics = salesDataService.getTotalSalesByRegion();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 판매원별 총 매출을 조회하는 API
     * 
     * @return 판매원별 총 매출 정보
     */
    @GetMapping("/statistics/salesperson")
    public ResponseEntity<List<Object[]>> getTotalSalesBySalesperson() {
        try {
            List<Object[]> statistics = salesDataService.getTotalSalesBySalesperson();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 월별 매출 통계를 조회하는 API
     * 
     * @return 월별 매출 통계 정보
     */
    @GetMapping("/statistics/monthly")
    public ResponseEntity<List<Object[]>> getMonthlySalesStatistics() {
        try {
            List<Object[]> statistics = salesDataService.getMonthlySalesStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 전체 매출 통계를 조회하는 API
     * 
     * @return 전체 매출 통계 정보
     */
    @GetMapping("/statistics/overall")
    public ResponseEntity<SalesDataService.SalesStatistics> getOverallStatistics() {
        try {
            SalesDataService.SalesStatistics statistics = salesDataService.getOverallStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
