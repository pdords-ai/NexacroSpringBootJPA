package com.nexacro.repository;

import com.nexacro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 Repository 인터페이스
 * 
 * 사용자 데이터에 대한 데이터베이스 작업을 처리합니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자를 조회하는 메서드
     * 
     * @param email 조회할 이메일
     * @return 해당 이메일의 사용자 (Optional로 null 안전성 보장)
     */
    Optional<User> findByEmail(String email);

    /**
     * 이름으로 사용자를 조회하는 메서드
     * 
     * @param name 조회할 이름
     * @return 해당 이름의 사용자 목록
     */
    List<User> findByName(String name);

    /**
     * 이름에 특정 키워드가 포함된 사용자를 조회하는 메서드
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 사용자 목록
     */
    List<User> findByNameContainingIgnoreCase(String name);

    /**
     * 나이 범위로 사용자를 조회하는 메서드
     * 
     * @param minAge 최소 나이
     * @param maxAge 최대 나이
     * @return 해당 나이 범위의 사용자 목록
     */
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    /**
     * 성별로 사용자를 조회하는 메서드
     * 
     * @param gender 조회할 성별
     * @return 해당 성별의 사용자 목록
     */
    List<User> findByGender(String gender);

    /**
     * 이메일이 존재하는지 확인하는 메서드
     * 
     * @param email 확인할 이메일
     * @return 이메일 존재 여부
     */
    boolean existsByEmail(String email);

    /**
     * 커스텀 쿼리: 나이대별 사용자 수를 조회하는 메서드
     * 
     * @return 나이대별 사용자 수 정보
     */
    @Query("SELECT " +
           "CASE " +
           "  WHEN u.age < 20 THEN '10대' " +
           "  WHEN u.age < 30 THEN '20대' " +
           "  WHEN u.age < 40 THEN '30대' " +
           "  WHEN u.age < 50 THEN '40대' " +
           "  WHEN u.age < 60 THEN '50대' " +
           "  ELSE '60대 이상' " +
           "END as ageGroup, " +
           "COUNT(u) as userCount " +
           "FROM User u " +
           "WHERE u.age IS NOT NULL " +
           "GROUP BY " +
           "CASE " +
           "  WHEN u.age < 20 THEN '10대' " +
           "  WHEN u.age < 30 THEN '20대' " +
           "  WHEN u.age < 40 THEN '30대' " +
           "  WHEN u.age < 50 THEN '40대' " +
           "  WHEN u.age < 60 THEN '50대' " +
           "  ELSE '60대 이상' " +
           "END " +
           "ORDER BY userCount DESC")
    List<Object[]> findUserCountByAgeGroup();

    /**
     * 커스텀 쿼리: 성별별 사용자 수를 조회하는 메서드
     * 
     * @return 성별별 사용자 수 정보
     */
    @Query("SELECT u.gender, COUNT(u) as userCount " +
           "FROM User u " +
           "WHERE u.gender IS NOT NULL " +
           "GROUP BY u.gender " +
           "ORDER BY userCount DESC")
    List<Object[]> findUserCountByGender();

    /**
     * 커스텀 쿼리: 최근 가입한 사용자들을 조회하는 메서드
     * 
     * @param limit 조회할 개수
     * @return 최근 가입한 사용자 목록
     */
    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC")
    List<User> findRecentUsers(@Param("limit") int limit);

    /**
     * 커스텀 쿼리: 특정 조건으로 사용자를 검색하는 메서드
     * 
     * @param name 이름 (null이면 무시)
     * @param gender 성별 (null이면 무시)
     * @param minAge 최소 나이 (null이면 무시)
     * @param maxAge 최대 나이 (null이면 무시)
     * @return 검색 조건에 맞는 사용자 목록
     */
    @Query("SELECT u FROM User u WHERE " +
           "(:name IS NULL OR u.name LIKE %:name%) AND " +
           "(:gender IS NULL OR u.gender = :gender) AND " +
           "(:minAge IS NULL OR u.age >= :minAge) AND " +
           "(:maxAge IS NULL OR u.age <= :maxAge)")
    List<User> findUsersWithFilters(@Param("name") String name,
                                  @Param("gender") String gender,
                                  @Param("minAge") Integer minAge,
                                  @Param("maxAge") Integer maxAge);
}


