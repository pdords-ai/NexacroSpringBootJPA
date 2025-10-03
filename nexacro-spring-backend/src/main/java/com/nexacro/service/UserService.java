package com.nexacro.service;

import com.nexacro.entity.User;
import com.nexacro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 서비스 클래스
 * 
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 레이어입니다.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param userRepository 사용자 Repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 모든 사용자를 조회하는 메서드
     * 
     * @return 모든 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ID로 사용자를 조회하는 메서드
     * 
     * @param id 조회할 사용자 ID
     * @return 해당 ID의 사용자 (Optional로 null 안전성 보장)
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 이메일로 사용자를 조회하는 메서드
     * 
     * @param email 조회할 이메일
     * @return 해당 이메일의 사용자 (Optional로 null 안전성 보장)
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 새로운 사용자를 저장하는 메서드
     * 
     * @param user 저장할 사용자
     * @return 저장된 사용자
     * @throws RuntimeException 이메일이 이미 존재할 경우
     */
    public User saveUser(User user) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * 사용자 정보를 업데이트하는 메서드
     * 
     * @param id 업데이트할 사용자 ID
     * @param user 업데이트할 데이터
     * @return 업데이트된 사용자
     * @throws RuntimeException 해당 ID의 사용자가 없을 경우
     */
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + id));
        
        // 이메일 변경 시 중복 체크
        if (!existingUser.getEmail().equals(user.getEmail()) && 
            userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + user.getEmail());
        }
        
        // 기존 사용자 정보 업데이트
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());
        
        return userRepository.save(existingUser);
    }

    /**
     * 사용자를 삭제하는 메서드
     * 
     * @param id 삭제할 사용자 ID
     * @throws RuntimeException 해당 ID의 사용자가 없을 경우
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다. ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * 이름으로 사용자를 검색하는 메서드
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * 나이 범위로 사용자를 조회하는 메서드
     * 
     * @param minAge 최소 나이
     * @param maxAge 최대 나이
     * @return 해당 나이 범위의 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        return userRepository.findByAgeBetween(minAge, maxAge);
    }

    /**
     * 성별로 사용자를 조회하는 메서드
     * 
     * @param gender 조회할 성별
     * @return 해당 성별의 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByGender(String gender) {
        return userRepository.findByGender(gender);
    }

    /**
     * 복합 조건으로 사용자를 검색하는 메서드
     * 
     * @param name 이름 (null이면 무시)
     * @param gender 성별 (null이면 무시)
     * @param minAge 최소 나이 (null이면 무시)
     * @param maxAge 최대 나이 (null이면 무시)
     * @return 검색 조건에 맞는 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> getUsersWithFilters(String name, String gender, Integer minAge, Integer maxAge) {
        return userRepository.findUsersWithFilters(name, gender, minAge, maxAge);
    }

    /**
     * 나이대별 사용자 수를 조회하는 메서드
     * 
     * @return 나이대별 사용자 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getUserCountByAgeGroup() {
        return userRepository.findUserCountByAgeGroup();
    }

    /**
     * 성별별 사용자 수를 조회하는 메서드
     * 
     * @return 성별별 사용자 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getUserCountByGender() {
        return userRepository.findUserCountByGender();
    }

    /**
     * 최근 가입한 사용자들을 조회하는 메서드
     * 
     * @param limit 조회할 개수
     * @return 최근 가입한 사용자 목록
     */
    @Transactional(readOnly = true)
    public List<User> getRecentUsers(int limit) {
        return userRepository.findRecentUsers(limit);
    }

    /**
     * 전체 사용자 통계를 계산하는 메서드
     * 
     * @return 전체 사용자 통계 정보
     */
    @Transactional(readOnly = true)
    public UserStatistics getOverallStatistics() {
        List<User> allUsers = userRepository.findAll();
        
        int totalCount = allUsers.size();
        double averageAge = allUsers.stream()
                .filter(user -> user.getAge() != null)
                .mapToInt(User::getAge)
                .average()
                .orElse(0.0);
        
        long maleCount = allUsers.stream()
                .filter(user -> "남성".equals(user.getGender()))
                .count();
        
        long femaleCount = allUsers.stream()
                .filter(user -> "여성".equals(user.getGender()))
                .count();
        
        return new UserStatistics(totalCount, averageAge, (int) maleCount, (int) femaleCount);
    }

    /**
     * 사용자 통계 정보를 담는 내부 클래스
     */
    public static class UserStatistics {
        private final int totalCount;
        private final double averageAge;
        private final int maleCount;
        private final int femaleCount;

        public UserStatistics(int totalCount, double averageAge, int maleCount, int femaleCount) {
            this.totalCount = totalCount;
            this.averageAge = averageAge;
            this.maleCount = maleCount;
            this.femaleCount = femaleCount;
        }

        // Getter 메서드들
        public int getTotalCount() { return totalCount; }
        public double getAverageAge() { return averageAge; }
        public int getMaleCount() { return maleCount; }
        public int getFemaleCount() { return femaleCount; }
    }
}
