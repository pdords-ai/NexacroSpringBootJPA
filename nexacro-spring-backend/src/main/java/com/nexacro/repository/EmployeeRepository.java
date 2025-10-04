package com.nexacro.repository;

import com.nexacro.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 직원 Repository 인터페이스
 * 
 * 직원 데이터에 대한 데이터베이스 작업을 처리합니다.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * 사번으로 직원을 조회하는 메서드
     * 
     * @param employeeNumber 조회할 사번
     * @return 해당 사번의 직원 (Optional로 null 안전성 보장)
     */
    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    /**
     * 이름으로 직원을 조회하는 메서드
     * 
     * @param name 조회할 이름
     * @return 해당 이름의 직원 목록
     */
    List<Employee> findByName(String name);

    /**
     * 이름에 특정 키워드가 포함된 직원을 조회하는 메서드
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 직원 목록
     */
    List<Employee> findByNameContainingIgnoreCase(String name);

    /**
     * 부서로 직원을 조회하는 메서드
     * 
     * @param department 조회할 부서
     * @return 해당 부서의 직원 목록
     */
    List<Employee> findByDepartment(String department);

    /**
     * 직급으로 직원을 조회하는 메서드
     * 
     * @param position 조회할 직급
     * @return 해당 직급의 직원 목록
     */
    List<Employee> findByPosition(String position);

    /**
     * 재직상태로 직원을 조회하는 메서드
     * 
     * @param status 조회할 재직상태
     * @return 해당 재직상태의 직원 목록
     */
    List<Employee> findByStatus(String status);

    /**
     * 급여 범위로 직원을 조회하는 메서드
     * 
     * @param minSalary 최소 급여
     * @param maxSalary 최대 급여
     * @return 해당 급여 범위의 직원 목록
     */
    List<Employee> findBySalaryBetween(Long minSalary, Long maxSalary);

    /**
     * 입사일 범위로 직원을 조회하는 메서드
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 해당 기간에 입사한 직원 목록
     */
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 사번이 존재하는지 확인하는 메서드
     * 
     * @param employeeNumber 확인할 사번
     * @return 사번 존재 여부
     */
    boolean existsByEmployeeNumber(String employeeNumber);

    /**
     * 이메일로 직원을 조회하는 메서드
     * 
     * @param email 조회할 이메일
     * @return 해당 이메일의 직원 (Optional로 null 안전성 보장)
     */
    Optional<Employee> findByEmail(String email);

    /**
     * 커스텀 쿼리: 부서별 직원 수를 조회하는 메서드
     * 
     * @return 부서별 직원 수 정보
     */
    @Query("SELECT e.department, COUNT(e) as employeeCount " +
           "FROM Employee e " +
           "WHERE e.status = '재직' " +
           "GROUP BY e.department " +
           "ORDER BY employeeCount DESC")
    List<Object[]> findEmployeeCountByDepartment();

    /**
     * 커스텀 쿼리: 직급별 직원 수를 조회하는 메서드
     * 
     * @return 직급별 직원 수 정보
     */
    @Query("SELECT e.position, COUNT(e) as employeeCount " +
           "FROM Employee e " +
           "WHERE e.status = '재직' " +
           "GROUP BY e.position " +
           "ORDER BY employeeCount DESC")
    List<Object[]> findEmployeeCountByPosition();

    /**
     * 커스텀 쿼리: 재직상태별 직원 수를 조회하는 메서드
     * 
     * @return 재직상태별 직원 수 정보
     */
    @Query("SELECT e.status, COUNT(e) as employeeCount " +
           "FROM Employee e " +
           "GROUP BY e.status " +
           "ORDER BY employeeCount DESC")
    List<Object[]> findEmployeeCountByStatus();

    /**
     * 커스텀 쿼리: 급여 통계를 조회하는 메서드
     * 
     * @return 급여 통계 정보 (평균, 최대, 최소)
     */
    @Query("SELECT " +
           "AVG(e.salary) as avgSalary, " +
           "MAX(e.salary) as maxSalary, " +
           "MIN(e.salary) as minSalary, " +
           "COUNT(e) as totalCount " +
           "FROM Employee e " +
           "WHERE e.status = '재직'")
    List<Object[]> findSalaryStatistics();

    /**
     * 커스텀 쿼리: 최근 입사한 직원들을 조회하는 메서드
     * 
     * @param limit 조회할 개수
     * @return 최근 입사한 직원 목록
     */
    @Query("SELECT e FROM Employee e ORDER BY e.hireDate DESC")
    List<Employee> findRecentEmployees(@Param("limit") int limit);

    /**
     * 커스텀 쿼리: 특정 조건으로 직원을 검색하는 메서드
     * 
     * @param name 이름 (null이면 무시)
     * @param department 부서 (null이면 무시)
     * @param position 직급 (null이면 무시)
     * @param status 재직상태 (null이면 무시)
     * @param minSalary 최소 급여 (null이면 무시)
     * @param maxSalary 최대 급여 (null이면 무시)
     * @return 검색 조건에 맞는 직원 목록
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "(:name IS NULL OR e.name LIKE %:name%) AND " +
           "(:department IS NULL OR e.department = :department) AND " +
           "(:position IS NULL OR e.position = :position) AND " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:minSalary IS NULL OR e.salary >= :minSalary) AND " +
           "(:maxSalary IS NULL OR e.salary <= :maxSalary)")
    List<Employee> findEmployeesWithFilters(@Param("name") String name,
                                          @Param("department") String department,
                                          @Param("position") String position,
                                          @Param("status") String status,
                                          @Param("minSalary") Long minSalary,
                                          @Param("maxSalary") Long maxSalary);

    /**
     * 커스텀 쿼리: 퇴사 예정 직원을 조회하는 메서드 (퇴사일이 설정된 직원)
     * 
     * @return 퇴사 예정 직원 목록
     */
    @Query("SELECT e FROM Employee e WHERE e.resignationDate IS NOT NULL AND e.status = '재직'")
    List<Employee> findEmployeesWithResignationDate();

    /**
     * 커스텀 쿼리: 근속년수별 직원 수를 조회하는 메서드
     * 
     * @return 근속년수별 직원 수 정보
     */
    @Query("SELECT " +
           "CASE " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 1 THEN '1년 미만' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 3 THEN '1-3년' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 5 THEN '3-5년' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 10 THEN '5-10년' " +
           "  ELSE '10년 이상' " +
           "END as tenureGroup, " +
           "COUNT(e) as employeeCount " +
           "FROM Employee e " +
           "WHERE e.status = '재직' " +
           "GROUP BY " +
           "CASE " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 1 THEN '1년 미만' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 3 THEN '1-3년' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 5 THEN '3-5년' " +
           "  WHEN YEAR(CURRENT_DATE) - YEAR(e.hireDate) < 10 THEN '5-10년' " +
           "  ELSE '10년 이상' " +
           "END " +
           "ORDER BY employeeCount DESC")
    List<Object[]> findEmployeeCountByTenure();
}
