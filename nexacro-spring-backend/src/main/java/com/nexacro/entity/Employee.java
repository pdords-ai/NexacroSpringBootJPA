package com.nexacro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 직원 정보 엔티티 클래스
 * 
 * 이 클래스는 데이터베이스의 employees 테이블과 매핑됩니다.
 * 인사정보를 관리하는 엔티티입니다.
 */
@Entity
@Table(name = "employees")
public class Employee {

    /**
     * 직원 ID (기본키)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사번 (직원 고유 번호)
     */
    @Column(name = "employee_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "사번은 필수입니다")
    @Size(max = 20, message = "사번은 20자를 초과할 수 없습니다")
    private String employeeNumber;

    /**
     * 직원명
     */
    @Column(name = "name", nullable = false, length = 50)
    @NotBlank(message = "직원명은 필수입니다")
    @Size(max = 50, message = "직원명은 50자를 초과할 수 없습니다")
    private String name;

    /**
     * 주민등록번호 (암호화된 형태로 저장)
     */
    @Column(name = "ssn", nullable = false, length = 20)
    @NotBlank(message = "주민등록번호는 필수입니다")
    @Size(max = 20, message = "주민등록번호는 20자를 초과할 수 없습니다")
    private String ssn;

    /**
     * 부서
     */
    @Column(name = "department", nullable = false, length = 50)
    @NotBlank(message = "부서는 필수입니다")
    @Size(max = 50, message = "부서명은 50자를 초과할 수 없습니다")
    private String department;

    /**
     * 직급
     */
    @Column(name = "position", nullable = false, length = 50)
    @NotBlank(message = "직급은 필수입니다")
    @Size(max = 50, message = "직급은 50자를 초과할 수 없습니다")
    private String position;

    /**
     * 입사일
     */
    @Column(name = "hire_date", nullable = false)
    @NotNull(message = "입사일은 필수입니다")
    @PastOrPresent(message = "입사일은 현재 또는 과거 날짜여야 합니다")
    private LocalDate hireDate;

    /**
     * 퇴사일 (재직 중인 경우 null)
     */
    @Column(name = "resignation_date")
    @PastOrPresent(message = "퇴사일은 현재 또는 과거 날짜여야 합니다")
    private LocalDate resignationDate;

    /**
     * 급여
     */
    @Column(name = "salary", nullable = false)
    @NotNull(message = "급여는 필수입니다")
    @Min(value = 0, message = "급여는 0 이상이어야 합니다")
    private Long salary;

    /**
     * 이메일
     */
    @Column(name = "email", length = 100)
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @Size(max = 100, message = "이메일은 100자를 초과할 수 없습니다")
    private String email;

    /**
     * 전화번호
     */
    @Column(name = "phone", length = 20)
    @Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다")
    private String phone;

    /**
     * 주소
     */
    @Column(name = "address", length = 200)
    @Size(max = 200, message = "주소는 200자를 초과할 수 없습니다")
    private String address;

    /**
     * 긴급연락처
     */
    @Column(name = "emergency_contact", length = 20)
    @Size(max = 20, message = "긴급연락처는 20자를 초과할 수 없습니다")
    private String emergencyContact;

    /**
     * 긴급연락처 관계
     */
    @Column(name = "emergency_relation", length = 20)
    @Size(max = 20, message = "긴급연락처 관계는 20자를 초과할 수 없습니다")
    private String emergencyRelation;

    /**
     * 재직상태 (재직, 휴직, 퇴직)
     */
    @Column(name = "status", nullable = false, length = 20)
    @NotBlank(message = "재직상태는 필수입니다")
    @Size(max = 20, message = "재직상태는 20자를 초과할 수 없습니다")
    private String status;

    /**
     * 생성일시
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 수정일시
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 기본 생성자
     */
    public Employee() {
    }

    /**
     * 전체 필드를 포함한 생성자
     */
    public Employee(String employeeNumber, String name, String ssn, String department, 
                   String position, LocalDate hireDate, Long salary, String email, 
                   String phone, String address, String emergencyContact, 
                   String emergencyRelation, String status) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.ssn = ssn;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.emergencyRelation = emergencyRelation;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 저장되기 전에 실행되는 메서드
     * 생성일시를 자동으로 설정합니다
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 업데이트되기 전에 실행되는 메서드
     * 수정일시를 자동으로 설정합니다
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getter와 Setter 메서드들

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getResignationDate() {
        return resignationDate;
    }

    public void setResignationDate(LocalDate resignationDate) {
        this.resignationDate = resignationDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", hireDate=" + hireDate +
                ", resignationDate=" + resignationDate +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyRelation='" + emergencyRelation + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
