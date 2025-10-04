package com.nexacro.service;

import com.nexacro.entity.Employee;
import com.nexacro.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 직원 서비스 클래스
 * 
 * 직원 관련 비즈니스 로직을 처리하는 서비스 레이어입니다.
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * 생성자 주입을 통한 의존성 주입
     * 
     * @param employeeRepository 직원 Repository
     */
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * 모든 직원을 조회하는 메서드
     * 
     * @return 모든 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * ID로 직원을 조회하는 메서드
     * 
     * @param id 조회할 직원 ID
     * @return 해당 ID의 직원 (Optional로 null 안전성 보장)
     */
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * 사번으로 직원을 조회하는 메서드
     * 
     * @param employeeNumber 조회할 사번
     * @return 해당 사번의 직원 (Optional로 null 안전성 보장)
     */
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeByEmployeeNumber(String employeeNumber) {
        return employeeRepository.findByEmployeeNumber(employeeNumber);
    }

    /**
     * 새로운 직원을 저장하는 메서드
     * 
     * @param employee 저장할 직원
     * @return 저장된 직원
     * @throws RuntimeException 사번이 이미 존재할 경우
     */
    public Employee saveEmployee(Employee employee) {
        // 사번 중복 체크
        if (employeeRepository.existsByEmployeeNumber(employee.getEmployeeNumber())) {
            throw new RuntimeException("이미 존재하는 사번입니다: " + employee.getEmployeeNumber());
        }
        
        // 이메일 중복 체크 (이메일이 있는 경우)
        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
            Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
            if (existingEmployee.isPresent()) {
                throw new RuntimeException("이미 존재하는 이메일입니다: " + employee.getEmail());
            }
        }
        
        return employeeRepository.save(employee);
    }

    /**
     * 직원 정보를 업데이트하는 메서드
     * 
     * @param id 업데이트할 직원 ID
     * @param employee 업데이트할 데이터
     * @return 업데이트된 직원
     * @throws RuntimeException 해당 ID의 직원이 없을 경우
     */
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. ID: " + id));
        
        // 사번 변경 시 중복 체크
        if (!existingEmployee.getEmployeeNumber().equals(employee.getEmployeeNumber()) && 
            employeeRepository.existsByEmployeeNumber(employee.getEmployeeNumber())) {
            throw new RuntimeException("이미 존재하는 사번입니다: " + employee.getEmployeeNumber());
        }
        
        // 이메일 변경 시 중복 체크 (이메일이 있는 경우)
        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
            if (!employee.getEmail().equals(existingEmployee.getEmail())) {
                Optional<Employee> emailExists = employeeRepository.findByEmail(employee.getEmail());
                if (emailExists.isPresent()) {
                    throw new RuntimeException("이미 존재하는 이메일입니다: " + employee.getEmail());
                }
            }
        }
        
        // 기존 직원 정보 업데이트
        existingEmployee.setEmployeeNumber(employee.getEmployeeNumber());
        existingEmployee.setName(employee.getName());
        existingEmployee.setSsn(employee.getSsn());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setHireDate(employee.getHireDate());
        existingEmployee.setResignationDate(employee.getResignationDate());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setEmergencyContact(employee.getEmergencyContact());
        existingEmployee.setEmergencyRelation(employee.getEmergencyRelation());
        existingEmployee.setStatus(employee.getStatus());
        
        return employeeRepository.save(existingEmployee);
    }

    /**
     * 직원을 삭제하는 메서드
     * 
     * @param id 삭제할 직원 ID
     * @throws RuntimeException 해당 ID의 직원이 없을 경우
     */
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("직원을 찾을 수 없습니다. ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    /**
     * 이름으로 직원을 검색하는 메서드
     * 
     * @param name 검색할 이름 키워드
     * @return 해당 키워드가 포함된 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * 부서로 직원을 조회하는 메서드
     * 
     * @param department 조회할 부서
     * @return 해당 부서의 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    /**
     * 직급으로 직원을 조회하는 메서드
     * 
     * @param position 조회할 직급
     * @return 해당 직급의 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    /**
     * 재직상태로 직원을 조회하는 메서드
     * 
     * @param status 조회할 재직상태
     * @return 해당 재직상태의 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByStatus(String status) {
        return employeeRepository.findByStatus(status);
    }

    /**
     * 급여 범위로 직원을 조회하는 메서드
     * 
     * @param minSalary 최소 급여
     * @param maxSalary 최대 급여
     * @return 해당 급여 범위의 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryRange(Long minSalary, Long maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }

    /**
     * 입사일 범위로 직원을 조회하는 메서드
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 해당 기간에 입사한 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByHireDateRange(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByHireDateBetween(startDate, endDate);
    }

    /**
     * 복합 조건으로 직원을 검색하는 메서드
     * 
     * @param name 이름 (null이면 무시)
     * @param department 부서 (null이면 무시)
     * @param position 직급 (null이면 무시)
     * @param status 재직상태 (null이면 무시)
     * @param minSalary 최소 급여 (null이면 무시)
     * @param maxSalary 최대 급여 (null이면 무시)
     * @return 검색 조건에 맞는 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesWithFilters(String name, String department, String position, 
                                                String status, Long minSalary, Long maxSalary) {
        return employeeRepository.findEmployeesWithFilters(name, department, position, status, minSalary, maxSalary);
    }

    /**
     * 부서별 직원 수를 조회하는 메서드
     * 
     * @return 부서별 직원 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getEmployeeCountByDepartment() {
        return employeeRepository.findEmployeeCountByDepartment();
    }

    /**
     * 직급별 직원 수를 조회하는 메서드
     * 
     * @return 직급별 직원 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getEmployeeCountByPosition() {
        return employeeRepository.findEmployeeCountByPosition();
    }

    /**
     * 재직상태별 직원 수를 조회하는 메서드
     * 
     * @return 재직상태별 직원 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getEmployeeCountByStatus() {
        return employeeRepository.findEmployeeCountByStatus();
    }

    /**
     * 급여 통계를 조회하는 메서드
     * 
     * @return 급여 통계 정보 (평균, 최대, 최소)
     */
    @Transactional(readOnly = true)
    public List<Object[]> getSalaryStatistics() {
        return employeeRepository.findSalaryStatistics();
    }

    /**
     * 최근 입사한 직원들을 조회하는 메서드
     * 
     * @param limit 조회할 개수
     * @return 최근 입사한 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getRecentEmployees(int limit) {
        return employeeRepository.findRecentEmployees(limit);
    }

    /**
     * 퇴사 예정 직원을 조회하는 메서드
     * 
     * @return 퇴사 예정 직원 목록
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesWithResignationDate() {
        return employeeRepository.findEmployeesWithResignationDate();
    }

    /**
     * 근속년수별 직원 수를 조회하는 메서드
     * 
     * @return 근속년수별 직원 수 정보
     */
    @Transactional(readOnly = true)
    public List<Object[]> getEmployeeCountByTenure() {
        return employeeRepository.findEmployeeCountByTenure();
    }

    /**
     * 직원 퇴사 처리 메서드
     * 
     * @param id 퇴사할 직원 ID
     * @param resignationDate 퇴사일
     * @return 퇴사 처리된 직원
     * @throws RuntimeException 해당 ID의 직원이 없을 경우
     */
    public Employee resignEmployee(Long id, LocalDate resignationDate) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. ID: " + id));
        
        employee.setResignationDate(resignationDate);
        employee.setStatus("퇴직");
        
        return employeeRepository.save(employee);
    }

    /**
     * 직원 복직 처리 메서드
     * 
     * @param id 복직할 직원 ID
     * @return 복직 처리된 직원
     * @throws RuntimeException 해당 ID의 직원이 없을 경우
     */
    public Employee rehireEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. ID: " + id));
        
        employee.setResignationDate(null);
        employee.setStatus("재직");
        
        return employeeRepository.save(employee);
    }

    /**
     * 전체 직원 통계를 계산하는 메서드
     * 
     * @return 전체 직원 통계 정보
     */
    @Transactional(readOnly = true)
    public EmployeeStatistics getOverallStatistics() {
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> activeEmployees = employeeRepository.findByStatus("재직");
        
        int totalCount = allEmployees.size();
        int activeCount = activeEmployees.size();
        int resignedCount = allEmployees.size() - activeCount;
        
        double averageSalary = activeEmployees.stream()
                .filter(emp -> emp.getSalary() != null)
                .mapToLong(Employee::getSalary)
                .average()
                .orElse(0.0);
        
        long maxSalary = activeEmployees.stream()
                .filter(emp -> emp.getSalary() != null)
                .mapToLong(Employee::getSalary)
                .max()
                .orElse(0L);
        
        long minSalary = activeEmployees.stream()
                .filter(emp -> emp.getSalary() != null)
                .mapToLong(Employee::getSalary)
                .min()
                .orElse(0L);
        
        return new EmployeeStatistics(totalCount, activeCount, resignedCount, 
                                    averageSalary, maxSalary, minSalary);
    }

    /**
     * 직원 통계 정보를 담는 내부 클래스
     */
    public static class EmployeeStatistics {
        private final int totalCount;
        private final int activeCount;
        private final int resignedCount;
        private final double averageSalary;
        private final long maxSalary;
        private final long minSalary;

        public EmployeeStatistics(int totalCount, int activeCount, int resignedCount, 
                                double averageSalary, long maxSalary, long minSalary) {
            this.totalCount = totalCount;
            this.activeCount = activeCount;
            this.resignedCount = resignedCount;
            this.averageSalary = averageSalary;
            this.maxSalary = maxSalary;
            this.minSalary = minSalary;
        }

        // Getter 메서드들
        public int getTotalCount() { return totalCount; }
        public int getActiveCount() { return activeCount; }
        public int getResignedCount() { return resignedCount; }
        public double getAverageSalary() { return averageSalary; }
        public long getMaxSalary() { return maxSalary; }
        public long getMinSalary() { return minSalary; }
    }
}
