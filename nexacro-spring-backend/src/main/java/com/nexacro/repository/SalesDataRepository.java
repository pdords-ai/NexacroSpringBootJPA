package com.nexacro.repository;

import com.nexacro.entity.SalesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 판매 데이터 Repository 인터페이스
 * 
 * JpaRepository를 상속받아 기본적인 CRUD 작업을 자동으로 제공받습니다.
 * 추가적인 쿼리 메서드나 커스텀 쿼리를 정의할 수 있습니다.
 */
@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Long> {

    /**
     * 카테고리별로 판매 데이터를 조회하는 메서드
     * 
     * @param category 조회할 카테고리
     * @return 해당 카테고리의 판매 데이터 목록
     */
    List<SalesData> findByCategory(String category);

    /**
     * 지역별로 판매 데이터를 조회하는 메서드
     * 
     * @param region 조회할 지역
     * @return 해당 지역의 판매 데이터 목록
     */
    List<SalesData> findByRegion(String region);

    /**
     * 판매원별로 판매 데이터를 조회하는 메서드
     * 
     * @param salesperson 조회할 판매원
     * @return 해당 판매원의 판매 데이터 목록
     */
    List<SalesData> findBySalesperson(String salesperson);

    /**
     * 상태별로 판매 데이터를 조회하는 메서드
     * 
     * @param status 조회할 상태
     * @return 해당 상태의 판매 데이터 목록
     */
    List<SalesData> findByStatus(String status);

    /**
     * 특정 기간의 판매 데이터를 조회하는 메서드
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 해당 기간의 판매 데이터 목록
     */
    List<SalesData> findBySalesDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 제품명에 특정 키워드가 포함된 판매 데이터를 조회하는 메서드
     * 
     * @param productName 검색할 제품명 키워드
     * @return 해당 키워드가 포함된 판매 데이터 목록
     */
    List<SalesData> findByProductNameContainingIgnoreCase(String productName);

    /**
     * 가격 범위로 판매 데이터를 조회하는 메서드
     * 
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 해당 가격 범위의 판매 데이터 목록
     */
    List<SalesData> findByPriceBetween(Integer minPrice, Integer maxPrice);

    /**
     * 복합 조건으로 판매 데이터를 조회하는 메서드
     * 카테고리와 지역을 동시에 조건으로 사용
     * 
     * @param category 조회할 카테고리
     * @param region 조회할 지역
     * @return 해당 조건을 만족하는 판매 데이터 목록
     */
    List<SalesData> findByCategoryAndRegion(String category, String region);

    /**
     * 커스텀 쿼리: 카테고리별 총 매출을 조회하는 메서드
     * 
     * @return 카테고리별 총 매출 정보
     */
    @Query("SELECT s.category, SUM(s.price * s.quantity) as totalSales " +
           "FROM SalesData s " +
           "GROUP BY s.category " +
           "ORDER BY totalSales DESC")
    List<Object[]> findTotalSalesByCategory();

    /**
     * 커스텀 쿼리: 지역별 총 매출을 조회하는 메서드
     * 
     * @return 지역별 총 매출 정보
     */
    @Query("SELECT s.region, SUM(s.price * s.quantity) as totalSales " +
           "FROM SalesData s " +
           "GROUP BY s.region " +
           "ORDER BY totalSales DESC")
    List<Object[]> findTotalSalesByRegion();

    /**
     * 커스텀 쿼리: 판매원별 총 매출을 조회하는 메서드
     * 
     * @return 판매원별 총 매출 정보
     */
    @Query("SELECT s.salesperson, SUM(s.price * s.quantity) as totalSales " +
           "FROM SalesData s " +
           "GROUP BY s.salesperson " +
           "ORDER BY totalSales DESC")
    List<Object[]> findTotalSalesBySalesperson();

    /**
     * 커스텀 쿼리: 월별 매출 통계를 조회하는 메서드
     * 
     * @return 월별 매출 통계 정보
     */
    @Query("SELECT YEAR(s.salesDate) as year, MONTH(s.salesDate) as month, " +
           "SUM(s.price * s.quantity) as totalSales " +
           "FROM SalesData s " +
           "GROUP BY YEAR(s.salesDate), MONTH(s.salesDate) " +
           "ORDER BY year DESC, month DESC")
    List<Object[]> findMonthlySalesStatistics();

    /**
     * 커스텀 쿼리: 특정 조건으로 판매 데이터를 검색하는 메서드
     * 
     * @param category 카테고리 (null이면 무시)
     * @param region 지역 (null이면 무시)
     * @param status 상태 (null이면 무시)
     * @return 검색 조건에 맞는 판매 데이터 목록
     */
    @Query("SELECT s FROM SalesData s WHERE " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:region IS NULL OR s.region = :region) AND " +
           "(:status IS NULL OR s.status = :status)")
    List<SalesData> findSalesDataWithFilters(@Param("category") String category,
                                           @Param("region") String region,
                                           @Param("status") String status);

    /**
     * 커스텀 쿼리: 상위 N개 판매 데이터를 조회하는 메서드
     * 
     * @param limit 조회할 개수
     * @return 상위 N개 판매 데이터 목록
     */
    @Query("SELECT s FROM SalesData s ORDER BY s.price * s.quantity DESC")
    List<SalesData> findTopSalesData(@Param("limit") int limit);
}


