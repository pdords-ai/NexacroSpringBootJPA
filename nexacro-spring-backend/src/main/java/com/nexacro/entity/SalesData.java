package com.nexacro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * 판매 데이터 엔티티 클래스
 * 
 * 이 클래스는 데이터베이스의 sales_data 테이블과 매핑됩니다.
 * JPA를 사용하여 객체와 관계형 데이터베이스 간의 매핑을 처리합니다.
 */
@Entity
@Table(name = "sales_data")
public class SalesData {

    /**
     * 판매 ID (기본키)
     * @GeneratedValue: 자동으로 값이 생성됩니다 (AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 제품명
     * @Column: 컬럼명을 지정하고 길이 제한을 설정합니다
     * @NotBlank: null, 빈 문자열, 공백만 있는 문자열을 허용하지 않습니다
     */
    @Column(name = "product_name", nullable = false, length = 100)
    @NotBlank(message = "제품명은 필수입니다")
    @Size(max = 100, message = "제품명은 100자를 초과할 수 없습니다")
    private String productName;

    /**
     * 카테고리
     */
    @Column(name = "category", nullable = false, length = 50)
    @NotBlank(message = "카테고리는 필수입니다")
    @Size(max = 50, message = "카테고리는 50자를 초과할 수 없습니다")
    private String category;

    /**
     * 가격
     * @Min: 최소값 제한
     */
    @Column(name = "price", nullable = false)
    @NotNull(message = "가격은 필수입니다")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private Integer price;

    /**
     * 수량
     */
    @Column(name = "quantity", nullable = false)
    @NotNull(message = "수량은 필수입니다")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다")
    private Integer quantity;

    /**
     * 총액 (가격 × 수량)
     * 계산된 필드이므로 데이터베이스에 저장하지 않습니다
     */
    @Transient
    private Integer total;

    /**
     * 판매일
     * @PastOrPresent: 현재 또는 과거 날짜만 허용
     */
    @Column(name = "sales_date", nullable = false)
    @NotNull(message = "판매일은 필수입니다")
    @PastOrPresent(message = "판매일은 현재 또는 과거 날짜여야 합니다")
    private LocalDate salesDate;

    /**
     * 판매원
     */
    @Column(name = "salesperson", nullable = false, length = 50)
    @NotBlank(message = "판매원은 필수입니다")
    @Size(max = 50, message = "판매원명은 50자를 초과할 수 없습니다")
    private String salesperson;

    /**
     * 지역
     */
    @Column(name = "region", nullable = false, length = 50)
    @NotBlank(message = "지역은 필수입니다")
    @Size(max = 50, message = "지역명은 50자를 초과할 수 없습니다")
    private String region;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 20)
    @NotBlank(message = "상태는 필수입니다")
    @Size(max = 20, message = "상태는 20자를 초과할 수 없습니다")
    private String status;

    /**
     * 기본 생성자
     * JPA에서 엔티티를 생성할 때 필요합니다
     */
    public SalesData() {
    }

    /**
     * 전체 필드를 포함한 생성자
     */
    public SalesData(String productName, String category, Integer price, 
                    Integer quantity, LocalDate salesDate, String salesperson, 
                    String region, String status) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.salesDate = salesDate;
        this.salesperson = salesperson;
        this.region = region;
        this.status = status;
        // 총액 자동 계산
        this.total = price * quantity;
    }

    /**
     * 총액을 계산하여 반환하는 메서드
     * @return 가격 × 수량
     */
    public Integer getTotal() {
        if (price != null && quantity != null) {
            return price * quantity;
        }
        return 0;
    }

    /**
     * 총액을 설정하는 메서드
     * @param total 설정할 총액
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    // Getter와 Setter 메서드들
    // Lombok을 사용하면 @Getter, @Setter 어노테이션으로 자동 생성 가능

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 객체의 문자열 표현을 반환하는 메서드
     * 디버깅이나 로깅에 유용합니다
     */
    @Override
    public String toString() {
        return "SalesData{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + getTotal() +
                ", salesDate=" + salesDate +
                ", salesperson='" + salesperson + '\'' +
                ", region='" + region + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
