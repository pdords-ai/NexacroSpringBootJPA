package com.nexacro.service;

import com.nexacro.entity.SalesData;
import com.nexacro.repository.SalesDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 판매 데이터 서비스 클래스
 * 
 * 비즈니스 로직을 처리하는 서비스 레이어입니다.
 * 트랜잭션 관리와 예외 처리를 담당합니다.
 */
@Service
@Transactional
public class SalesDataService {

    private final SalesDataRepository salesDataRepository;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param salesDataRepository 판매 데이터 Repository
     */
    @Autowired
    public SalesDataService(SalesDataRepository salesDataRepository) {
        this.salesDataRepository = salesDataRepository;
    }

    /**
     * 모든 판매 데이터를 조회하는 메서드
     * 
     * @return 모든 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getAllSalesData() {
        return salesDataRepository.findAll();
    }

    /**
     * ID로 판매 데이터를 조회하는 메서드
     * 
     * @param id 조회할 판매 데이터 ID
     * @return 해당 ID의 판매 데이터 (Optional로 null 안전성 보장)
     */
    @Transactional(readOnly = true)
    public Optional<SalesData> getSalesDataById(Long id) {
        return salesDataRepository.findById(id);
    }

    /**
     * 새로운 판매 데이터를 저장하는 메서드
     * 
     * @param salesData 저장할 판매 데이터
     * @return 저장된 판매 데이터
     */
    public SalesData saveSalesData(SalesData salesData) {
        // 총액 자동 계산
        salesData.setTotal(salesData.getPrice() * salesData.getQuantity());
        return salesDataRepository.save(salesData);
    }

    /**
     * 판매 데이터를 업데이트하는 메서드
     * 
     * @param id 업데이트할 판매 데이터 ID
     * @param salesData 업데이트할 데이터
     * @return 업데이트된 판매 데이터
     * @throws RuntimeException 해당 ID의 데이터가 없을 경우
     */
    public SalesData updateSalesData(Long id, SalesData salesData) {
        SalesData existingSalesData = salesDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("판매 데이터를 찾을 수 없습니다. ID: " + id));
        
        // 기존 데이터 업데이트
        existingSalesData.setProductName(salesData.getProductName());
        existingSalesData.setCategory(salesData.getCategory());
        existingSalesData.setPrice(salesData.getPrice());
        existingSalesData.setQuantity(salesData.getQuantity());
        existingSalesData.setSalesDate(salesData.getSalesDate());
        existingSalesData.setSalesperson(salesData.getSalesperson());
        existingSalesData.setRegion(salesData.getRegion());
        existingSalesData.setStatus(salesData.getStatus());
        
        // 총액 자동 계산
        existingSalesData.setTotal(existingSalesData.getPrice() * existingSalesData.getQuantity());
        
        return salesDataRepository.save(existingSalesData);
    }

    /**
     * 판매 데이터를 삭제하는 메서드
     * 
     * @param id 삭제할 판매 데이터 ID
     * @throws RuntimeException 해당 ID의 데이터가 없을 경우
     */
    public void deleteSalesData(Long id) {
        if (!salesDataRepository.existsById(id)) {
            throw new RuntimeException("판매 데이터를 찾을 수 없습니다. ID: " + id);
        }
        salesDataRepository.deleteById(id);
    }

    /**
     * 카테고리별로 판매 데이터를 조회하는 메서드
     * 
     * @param category 조회할 카테고리
     * @return 해당 카테고리의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataByCategory(String category) {
        return salesDataRepository.findByCategory(category);
    }

    /**
     * 지역별로 판매 데이터를 조회하는 메서드
     * 
     * @param region 조회할 지역
     * @return 해당 지역의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataByRegion(String region) {
        return salesDataRepository.findByRegion(region);
    }

    /**
     * 판매원별로 판매 데이터를 조회하는 메서드
     * 
     * @param salesperson 조회할 판매원
     * @return 해당 판매원의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataBySalesperson(String salesperson) {
        return salesDataRepository.findBySalesperson(salesperson);
    }

    /**
     * 상태별로 판매 데이터를 조회하는 메서드
     * 
     * @param status 조회할 상태
     * @return 해당 상태의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataByStatus(String status) {
        return salesDataRepository.findByStatus(status);
    }

    /**
     * 특정 기간의 판매 데이터를 조회하는 메서드
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 해당 기간의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesDataRepository.findBySalesDateBetween(startDate, endDate);
    }

    /**
     * 제품명으로 판매 데이터를 검색하는 메서드
     * 
     * @param productName 검색할 제품명 키워드
     * @return 해당 키워드가 포함된 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> searchSalesDataByProductName(String productName) {
        return salesDataRepository.findByProductNameContainingIgnoreCase(productName);
    }

    /**
     * 가격 범위로 판매 데이터를 조회하는 메서드
     * 
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 해당 가격 범위의 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataByPriceRange(Integer minPrice, Integer maxPrice) {
        return salesDataRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * 복합 조건으로 판매 데이터를 조회하는 메서드
     * 
     * @param category 카테고리 (null이면 무시)
     * @param region 지역 (null이면 무시)
     * @param status 상태 (null이면 무시)
     * @return 검색 조건에 맞는 판매 데이터 목록
     */
    @Transactional(readOnly = true)
    public List<SalesData> getSalesDataWithFilters(String category, String region, String status) {
        return salesDataRepository.findSalesDataWithFilters(category, region, status);
    }

    /**
     * 카테고리별 총 매출을 조회하는 메서드
     * 
     * @return 카테고리별 총 매출 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getTotalSalesByCategory() {
        return salesDataRepository.findTotalSalesByCategory();
    }

    /**
     * 지역별 총 매출을 조회하는 메서드
     * 
     * @return 지역별 총 매출 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getTotalSalesByRegion() {
        return salesDataRepository.findTotalSalesByRegion();
    }

    /**
     * 판매원별 총 매출을 조회하는 메서드
     * 
     * @return 판매원별 총 매출 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getTotalSalesBySalesperson() {
        return salesDataRepository.findTotalSalesBySalesperson();
    }

    /**
     * 월별 매출 통계를 조회하는 메서드
     * 
     * @return 월별 매출 통계 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getMonthlySalesStatistics() {
        return salesDataRepository.findMonthlySalesStatistics();
    }

    /**
     * 전체 매출 통계를 계산하는 메서드
     * 
     * @return 전체 매출 통계 정보
     */
    @Transactional(readOnly = true)
    public SalesStatistics getOverallStatistics() {
        List<SalesData> allSalesData = salesDataRepository.findAll();
        
        int totalCount = allSalesData.size();
        int totalSales = allSalesData.stream()
                .mapToInt(sales -> sales.getPrice() * sales.getQuantity())
                .sum();
        double averageSales = totalCount > 0 ? (double) totalSales / totalCount : 0;
        
        int maxSales = allSalesData.stream()
                .mapToInt(sales -> sales.getPrice() * sales.getQuantity())
                .max()
                .orElse(0);
        
        int minSales = allSalesData.stream()
                .mapToInt(sales -> sales.getPrice() * sales.getQuantity())
                .min()
                .orElse(0);
        
        return new SalesStatistics(totalCount, totalSales, averageSales, maxSales, minSales);
    }

    /**
     * 판매 통계 정보를 담는 내부 클래스
     */
    public static class SalesStatistics {
        private final int totalCount;
        private final int totalSales;
        private final double averageSales;
        private final int maxSales;
        private final int minSales;

        public SalesStatistics(int totalCount, int totalSales, double averageSales, int maxSales, int minSales) {
            this.totalCount = totalCount;
            this.totalSales = totalSales;
            this.averageSales = averageSales;
            this.maxSales = maxSales;
            this.minSales = minSales;
        }

        // Getter 메서드들
        public int getTotalCount() { return totalCount; }
        public int getTotalSales() { return totalSales; }
        public double getAverageSales() { return averageSales; }
        public int getMaxSales() { return maxSales; }
        public int getMinSales() { return minSales; }
    }
}
