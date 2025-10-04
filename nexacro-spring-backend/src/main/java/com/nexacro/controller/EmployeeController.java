package com.nexacro.controller;

import com.nexacro.entity.Employee;
import com.nexacro.service.EmployeeService;
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
 * 직원 REST API 컨트롤러
 * 
 * Nexacro 그리드에서 사용할 수 있는 직원 관리 REST API 엔드포인트를 제공합니다.
 * CORS 설정을 통해 Nexacro 애플리케이션에서 접근할 수 있습니다.
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // CORS 설정 - 모든 도메인에서 접근 허용
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param employeeService 직원 서비스
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 모든 직원을 조회하는 API
     * 
     * @return 모든 직원 목록
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employeeList = employeeService.getAllEmployees();
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ID로 직원을 조회하는 API
     * 
     * @param id 조회할 직원 ID
     * @return 해당 ID의 직원
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            return employee.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사번으로 직원을 조회하는 API
     * 
     * @param employeeNumber 조회할 사번
     * @return 해당 사번의 직원
     */
    @GetMapping("/employee-number/{employeeNumber}")
    public ResponseEntity<Employee> getEmployeeByEmployeeNumber(@PathVariable String employeeNumber) {
        try {
            Optional<Employee> employee = employeeService.getEmployeeByEmployeeNumber(employeeNumber);
            return employee.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 새로운 직원을 생성하는 API
     * 
     * @param employee 생성할 직원
     * @return 생성된 직원
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 직원 정보를 업데이트하는 API
     * 
     * @param id 업데이트할 직원 ID
     * @param employee 업데이트할 데이터
     * @return 업데이트된 직원
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, 
                                                 @Valid @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 직원을 삭제하는 API
     * 
     * @param id 삭제할 직원 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "직원이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 이름으로 직원을 검색하는 API
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 직원 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        try {
            List<Employee> employeeList = employeeService.searchEmployeesByName(name);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 부서로 직원을 조회하는 API
     * 
     * @param department 조회할 부서
     * @return 해당 부서의 직원 목록
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        try {
            List<Employee> employeeList = employeeService.getEmployeesByDepartment(department);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 직급으로 직원을 조회하는 API
     * 
     * @param position 조회할 직급
     * @return 해당 직급의 직원 목록
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<List<Employee>> getEmployeesByPosition(@PathVariable String position) {
        try {
            List<Employee> employeeList = employeeService.getEmployeesByPosition(position);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 재직상태로 직원을 조회하는 API
     * 
     * @param status 조회할 재직상태
     * @return 해당 재직상태의 직원 목록
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> getEmployeesByStatus(@PathVariable String status) {
        try {
            List<Employee> employeeList = employeeService.getEmployeesByStatus(status);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 급여 범위로 직원을 조회하는 API
     * 
     * @param minSalary 최소 급여
     * @param maxSalary 최대 급여
     * @return 해당 급여 범위의 직원 목록
     */
    @GetMapping("/salary-range")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam Long minSalary,
            @RequestParam Long maxSalary) {
        try {
            List<Employee> employeeList = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 입사일 범위로 직원을 조회하는 API
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 해당 기간에 입사한 직원 목록
     */
    @GetMapping("/hire-date-range")
    public ResponseEntity<List<Employee>> getEmployeesByHireDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<Employee> employeeList = employeeService.getEmployeesByHireDateRange(start, end);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 복합 조건으로 직원을 검색하는 API
     * 
     * @param name 이름 (선택사항)
     * @param department 부서 (선택사항)
     * @param position 직급 (선택사항)
     * @param status 재직상태 (선택사항)
     * @param minSalary 최소 급여 (선택사항)
     * @param maxSalary 최대 급여 (선택사항)
     * @return 검색 조건에 맞는 직원 목록
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> getEmployeesWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long minSalary,
            @RequestParam(required = false) Long maxSalary) {
        try {
            List<Employee> employeeList = employeeService.getEmployeesWithFilters(
                name, department, position, status, minSalary, maxSalary);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 최근 입사한 직원들을 조회하는 API
     * 
     * @param limit 조회할 개수 (기본값: 10)
     * @return 최근 입사한 직원 목록
     */
    @GetMapping("/recent")
    public ResponseEntity<List<Employee>> getRecentEmployees(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Employee> employeeList = employeeService.getRecentEmployees(limit);
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 퇴사 예정 직원을 조회하는 API
     * 
     * @return 퇴사 예정 직원 목록
     */
    @GetMapping("/resignation-scheduled")
    public ResponseEntity<List<Employee>> getEmployeesWithResignationDate() {
        try {
            List<Employee> employeeList = employeeService.getEmployeesWithResignationDate();
            return ResponseEntity.ok(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 직원 퇴사 처리 API
     * 
     * @param id 퇴사할 직원 ID
     * @param resignationDate 퇴사일
     * @return 퇴사 처리된 직원
     */
    @PutMapping("/{id}/resign")
    public ResponseEntity<Employee> resignEmployee(@PathVariable Long id, 
                                                 @RequestParam String resignationDate) {
        try {
            LocalDate resignation = LocalDate.parse(resignationDate);
            Employee resignedEmployee = employeeService.resignEmployee(id, resignation);
            return ResponseEntity.ok(resignedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 직원 복직 처리 API
     * 
     * @param id 복직할 직원 ID
     * @return 복직 처리된 직원
     */
    @PutMapping("/{id}/rehire")
    public ResponseEntity<Employee> rehireEmployee(@PathVariable Long id) {
        try {
            Employee rehiredEmployee = employeeService.rehireEmployee(id);
            return ResponseEntity.ok(rehiredEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 부서별 직원 수를 조회하는 API
     * 
     * @return 부서별 직원 수 정보
     */
    @GetMapping("/statistics/department")
    public ResponseEntity<List<Object[]>> getEmployeeCountByDepartment() {
        try {
            List<Object[]> statistics = employeeService.getEmployeeCountByDepartment();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 직급별 직원 수를 조회하는 API
     * 
     * @return 직급별 직원 수 정보
     */
    @GetMapping("/statistics/position")
    public ResponseEntity<List<Object[]>> getEmployeeCountByPosition() {
        try {
            List<Object[]> statistics = employeeService.getEmployeeCountByPosition();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 재직상태별 직원 수를 조회하는 API
     * 
     * @return 재직상태별 직원 수 정보
     */
    @GetMapping("/statistics/status")
    public ResponseEntity<List<Object[]>> getEmployeeCountByStatus() {
        try {
            List<Object[]> statistics = employeeService.getEmployeeCountByStatus();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 급여 통계를 조회하는 API
     * 
     * @return 급여 통계 정보 (평균, 최대, 최소)
     */
    @GetMapping("/statistics/salary")
    public ResponseEntity<List<Object[]>> getSalaryStatistics() {
        try {
            List<Object[]> statistics = employeeService.getSalaryStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 근속년수별 직원 수를 조회하는 API
     * 
     * @return 근속년수별 직원 수 정보
     */
    @GetMapping("/statistics/tenure")
    public ResponseEntity<List<Object[]>> getEmployeeCountByTenure() {
        try {
            List<Object[]> statistics = employeeService.getEmployeeCountByTenure();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 전체 직원 통계를 조회하는 API
     * 
     * @return 전체 직원 통계 정보
     */
    @GetMapping("/statistics/overall")
    public ResponseEntity<EmployeeService.EmployeeStatistics> getOverallStatistics() {
        try {
            EmployeeService.EmployeeStatistics statistics = employeeService.getOverallStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
