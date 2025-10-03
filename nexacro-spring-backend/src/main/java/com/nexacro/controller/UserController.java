package com.nexacro.controller;

import com.nexacro.entity.User;
import com.nexacro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 사용자 REST API 컨트롤러
 * 
 * Nexacro 그리드에서 사용할 수 있는 사용자 관리 REST API 엔드포인트를 제공합니다.
 * CORS 설정을 통해 Nexacro 애플리케이션에서 접근할 수 있습니다.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // CORS 설정 - 모든 도메인에서 접근 허용
public class UserController {

    private final UserService userService;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param userService 사용자 서비스
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 모든 사용자를 조회하는 API
     * 
     * @return 모든 사용자 목록
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ID로 사용자를 조회하는 API
     * 
     * @param id 조회할 사용자 ID
     * @return 해당 ID의 사용자
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 이메일로 사용자를 조회하는 API
     * 
     * @param email 조회할 이메일
     * @return 해당 이메일의 사용자
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userService.getUserByEmail(email);
            return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 새로운 사용자를 생성하는 API
     * 
     * @param user 생성할 사용자
     * @return 생성된 사용자
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사용자 정보를 업데이트하는 API
     * 
     * @param id 업데이트할 사용자 ID
     * @param user 업데이트할 데이터
     * @return 업데이트된 사용자
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                         @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 사용자를 삭제하는 API
     * 
     * @param id 삭제할 사용자 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "사용자가 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 이름으로 사용자를 검색하는 API
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 사용자 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
        try {
            List<User> userList = userService.searchUsersByName(name);
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 나이 범위로 사용자를 조회하는 API
     * 
     * @param minAge 최소 나이
     * @param maxAge 최대 나이
     * @return 해당 나이 범위의 사용자 목록
     */
    @GetMapping("/age-range")
    public ResponseEntity<List<User>> getUsersByAgeRange(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        try {
            List<User> userList = userService.getUsersByAgeRange(minAge, maxAge);
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 성별로 사용자를 조회하는 API
     * 
     * @param gender 조회할 성별
     * @return 해당 성별의 사용자 목록
     */
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<User>> getUsersByGender(@PathVariable String gender) {
        try {
            List<User> userList = userService.getUsersByGender(gender);
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 복합 조건으로 사용자를 검색하는 API
     * 
     * @param name 이름 (선택사항)
     * @param gender 성별 (선택사항)
     * @param minAge 최소 나이 (선택사항)
     * @param maxAge 최대 나이 (선택사항)
     * @return 검색 조건에 맞는 사용자 목록
     */
    @GetMapping("/filter")
    public ResponseEntity<List<User>> getUsersWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        try {
            List<User> userList = userService.getUsersWithFilters(name, gender, minAge, maxAge);
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 최근 가입한 사용자들을 조회하는 API
     * 
     * @param limit 조회할 개수 (기본값: 10)
     * @return 최근 가입한 사용자 목록
     */
    @GetMapping("/recent")
    public ResponseEntity<List<User>> getRecentUsers(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<User> userList = userService.getRecentUsers(limit);
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 나이대별 사용자 수를 조회하는 API
     * 
     * @return 나이대별 사용자 수 정보
     */
    @GetMapping("/statistics/age-group")
    public ResponseEntity<List<Object[]>> getUserCountByAgeGroup() {
        try {
            List<Object[]> statistics = userService.getUserCountByAgeGroup();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 성별별 사용자 수를 조회하는 API
     * 
     * @return 성별별 사용자 수 정보
     */
    @GetMapping("/statistics/gender")
    public ResponseEntity<List<Object[]>> getUserCountByGender() {
        try {
            List<Object[]> statistics = userService.getUserCountByGender();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 전체 사용자 통계를 조회하는 API
     * 
     * @return 전체 사용자 통계 정보
     */
    @GetMapping("/statistics/overall")
    public ResponseEntity<UserService.UserStatistics> getOverallStatistics() {
        try {
            UserService.UserStatistics statistics = userService.getOverallStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
