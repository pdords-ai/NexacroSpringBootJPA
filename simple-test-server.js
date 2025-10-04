// 간단한 테스트용 API 서버 (Node.js)
// Java/Maven 설치 없이 테스트할 수 있습니다

const http = require('http');
const url = require('url');

// 샘플 직원 데이터
const employees = [
    {
        id: 1,
        employeeNumber: "EMP0001",
        name: "김철수",
        ssn: "123456-1234567",
        department: "개발팀",
        position: "사원",
        hireDate: "2024-01-01",
        resignationDate: null,
        salary: 40000000,
        email: "kim@company.com",
        phone: "010-1234-5678",
        address: "서울시 강남구",
        emergencyContact: "010-9876-5432",
        emergencyRelation: "부",
        status: "재직",
        createdAt: "2024-01-01T00:00:00",
        updatedAt: "2024-01-01T00:00:00"
    },
    {
        id: 2,
        employeeNumber: "EMP0002",
        name: "이영희",
        ssn: "234567-2345678",
        department: "마케팅팀",
        position: "대리",
        hireDate: "2023-06-15",
        resignationDate: null,
        salary: 45000000,
        email: "lee@company.com",
        phone: "010-2345-6789",
        address: "서울시 서초구",
        emergencyContact: "010-8765-4321",
        emergencyRelation: "모",
        status: "재직",
        createdAt: "2023-06-15T00:00:00",
        updatedAt: "2023-06-15T00:00:00"
    },
    {
        id: 3,
        employeeNumber: "EMP0003",
        name: "박민수",
        ssn: "345678-3456789",
        department: "영업팀",
        position: "과장",
        hireDate: "2022-03-10",
        resignationDate: null,
        salary: 55000000,
        email: "park@company.com",
        phone: "010-3456-7890",
        address: "경기도 성남시",
        emergencyContact: "010-7654-3210",
        emergencyRelation: "배우자",
        status: "재직",
        createdAt: "2022-03-10T00:00:00",
        updatedAt: "2022-03-10T00:00:00"
    }
];

// 통계 데이터
const statistics = {
    totalCount: employees.length,
    activeCount: employees.filter(emp => emp.status === "재직").length,
    resignedCount: employees.filter(emp => emp.status === "퇴직").length,
    averageSalary: employees.reduce((sum, emp) => sum + emp.salary, 0) / employees.length,
    maxSalary: Math.max(...employees.map(emp => emp.salary)),
    minSalary: Math.min(...employees.map(emp => emp.salary))
};

// CORS 헤더 설정
function setCorsHeaders(res) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    res.setHeader('Content-Type', 'application/json; charset=utf-8');
}

// 서버 생성
const server = http.createServer((req, res) => {
    setCorsHeaders(res);
    
    // OPTIONS 요청 처리 (CORS preflight)
    if (req.method === 'OPTIONS') {
        res.writeHead(200);
        res.end();
        return;
    }
    
    const parsedUrl = url.parse(req.url, true);
    const path = parsedUrl.pathname;
    const method = req.method;
    
    console.log(`${method} ${path}`);
    
    // API 라우팅
    if (path === '/api/employees' && method === 'GET') {
        // 전체 직원 목록 조회
        res.writeHead(200);
        res.end(JSON.stringify(employees));
        
    } else if (path.startsWith('/api/employees/') && method === 'GET') {
        // 특정 직원 조회
        const id = parseInt(path.split('/')[3]);
        const employee = employees.find(emp => emp.id === id);
        
        if (employee) {
            res.writeHead(200);
            res.end(JSON.stringify(employee));
        } else {
            res.writeHead(404);
            res.end(JSON.stringify({ error: 'Employee not found' }));
        }
        
    } else if (path === '/api/employees/filter' && method === 'GET') {
        // 필터링된 직원 목록 조회
        const query = parsedUrl.query;
        let filteredEmployees = [...employees];
        
        if (query.name) {
            filteredEmployees = filteredEmployees.filter(emp => 
                emp.name.includes(query.name)
            );
        }
        
        if (query.department) {
            filteredEmployees = filteredEmployees.filter(emp => 
                emp.department === query.department
            );
        }
        
        if (query.status) {
            filteredEmployees = filteredEmployees.filter(emp => 
                emp.status === query.status
            );
        }
        
        res.writeHead(200);
        res.end(JSON.stringify(filteredEmployees));
        
    } else if (path === '/api/employees/statistics/overall' && method === 'GET') {
        // 통계 정보 조회
        res.writeHead(200);
        res.end(JSON.stringify(statistics));
        
    } else if (path === '/api/employees' && method === 'POST') {
        // 새 직원 등록
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        
        req.on('end', () => {
            try {
                const newEmployee = JSON.parse(body);
                newEmployee.id = employees.length + 1;
                newEmployee.createdAt = new Date().toISOString();
                newEmployee.updatedAt = new Date().toISOString();
                
                employees.push(newEmployee);
                
                res.writeHead(201);
                res.end(JSON.stringify(newEmployee));
            } catch (error) {
                res.writeHead(400);
                res.end(JSON.stringify({ error: 'Invalid JSON' }));
            }
        });
        
    } else if (path.startsWith('/api/employees/') && method === 'PUT') {
        // 직원 정보 수정
        const id = parseInt(path.split('/')[3]);
        const employeeIndex = employees.findIndex(emp => emp.id === id);
        
        if (employeeIndex === -1) {
            res.writeHead(404);
            res.end(JSON.stringify({ error: 'Employee not found' }));
            return;
        }
        
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        
        req.on('end', () => {
            try {
                const updatedEmployee = JSON.parse(body);
                updatedEmployee.id = id;
                updatedEmployee.updatedAt = new Date().toISOString();
                
                employees[employeeIndex] = updatedEmployee;
                
                res.writeHead(200);
                res.end(JSON.stringify(updatedEmployee));
            } catch (error) {
                res.writeHead(400);
                res.end(JSON.stringify({ error: 'Invalid JSON' }));
            }
        });
        
    } else if (path.startsWith('/api/employees/') && method === 'DELETE') {
        // 직원 삭제
        const id = parseInt(path.split('/')[3]);
        const employeeIndex = employees.findIndex(emp => emp.id === id);
        
        if (employeeIndex === -1) {
            res.writeHead(404);
            res.end(JSON.stringify({ error: 'Employee not found' }));
        } else {
            employees.splice(employeeIndex, 1);
            res.writeHead(200);
            res.end(JSON.stringify({ message: 'Employee deleted successfully' }));
        }
        
    } else {
        // 404 Not Found
        res.writeHead(404);
        res.end(JSON.stringify({ error: 'Not Found' }));
    }
});

const PORT = 8080;

server.listen(PORT, () => {
    console.log(`🚀 테스트 서버가 http://localhost:${PORT} 에서 실행 중입니다!`);
    console.log(`📊 직원 API: http://localhost:${PORT}/api/employees`);
    console.log(`📈 통계 API: http://localhost:${PORT}/api/employees/statistics/overall`);
    console.log(`\n💡 넥사크로 페이지를 열어서 테스트해보세요!`);
    console.log(`   파일: d:\\workspace\\Nexacro\\01_기본_UI_컴포넌트\\16_인사정보관리.html`);
});

// 서버 종료 처리
process.on('SIGINT', () => {
    console.log('\n👋 서버를 종료합니다...');
    server.close(() => {
        console.log('✅ 서버가 정상적으로 종료되었습니다.');
        process.exit(0);
    });
});
