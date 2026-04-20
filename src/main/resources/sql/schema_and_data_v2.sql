-- =============================================
-- Script tạo bảng và dữ liệu mẫu cho Quản lý Học phần (NÂNG CẤP)
-- Phiên bản: 2.0
-- Mô tả: Cấu trúc mới cho phép thêm dữ liệu không giới hạn, logic chặt chẽ
-- =============================================

USE quanlyhocphan;
GO

-- =============================================
-- XÓA CÁC BẢNG CŨ (nếu tồn tại)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'lecturer_course_classes') DROP TABLE lecturer_course_classes;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'student_course_sections') DROP TABLE student_course_sections;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'course_sections') DROP TABLE course_sections;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'semesters') DROP TABLE semesters;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'school_years') DROP TABLE school_years;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'academic_years') DROP TABLE academic_years;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'courses') DROP TABLE courses;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'rooms') DROP TABLE rooms;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'buildings') DROP TABLE buildings;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'employees') DROP TABLE employees;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'students') DROP TABLE students;
GO

-- =============================================
-- 1. TẠO CÁC BẢNG CƠ BẢN (BASE TABLES)
-- =============================================

-- 1.1. Bảng buildings (Tòa nhà)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'buildings')
BEGIN
    CREATE TABLE buildings (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,  -- Mã tòa nhà: A, B, C...
        name NVARCHAR(255) NOT NULL,         -- Tên tòa nhà
        short_name NVARCHAR(50),             -- Tên viết tắt
        address NVARCHAR(500),               -- Địa chỉ
        floors INT,                          -- Số tầng
        description NVARCHAR(500),
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER
    );
    PRINT 'Created table: buildings';
END
GO

-- 1.2. Bảng rooms (Phòng học)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'rooms')
BEGIN
    CREATE TABLE rooms (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã phòng: A101, B202...
        name NVARCHAR(255),                  -- Tên phòng
        building_id UNIQUEIDENTIFIER,        -- Tòa nhà
        floor INT,                           -- Tầng
        capacity INT,                       -- Số chỗ ngồi
        room_type NVARCHAR(50),             -- Loại phòng: Lý thuyết, Thực hành, Lab...
        equipment NVARCHAR(500),            -- Trang thiết bị
        description NVARCHAR(500),
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        FOREIGN KEY (building_id) REFERENCES buildings(id)
    );
    PRINT 'Created table: rooms';
END
GO

-- 1.3. Bảng employees (Nhân viên/Giảng viên)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'employees')
BEGIN
    CREATE TABLE employees (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã nhân viên: NV001, GV001...
        employee_type NVARCHAR(50),          -- Loại: Giảng viên, Admin, NV...
        first_name NVARCHAR(100),            -- Họ
        last_name NVARCHAR(100),             -- Tên
        full_name AS first_name + ' ' + last_name PERSISTED,  -- Họ tên đầy đủ
        gender NVARCHAR(20),                 -- Giới tính
        date_of_birth DATE,                  -- Ngày sinh
        email NVARCHAR(255) UNIQUE,          -- Email
        phone NVARCHAR(20),                  -- Số điện thoại
        address NVARCHAR(500),               -- Địa chỉ
        department NVARCHAR(255),            -- Khoa/Bộ môn
        position NVARCHAR(255),              -- Chức vụ
        degree NVARCHAR(100),                -- Học vị: Thạc sĩ, Tiến sĩ...
        hire_date DATE,                      -- Ngày vào làm
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER
    );
    PRINT 'Created table: employees';
END
GO

-- 1.4. Bảng students (Sinh viên)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'students')
BEGIN
    CREATE TABLE students (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã sinh viên: SV001...
        first_name NVARCHAR(100),            -- Họ
        last_name NVARCHAR(100),             -- Tên
        full_name AS first_name + ' ' + last_name PERSISTED,  -- Họ tên đầy đủ
        gender NVARCHAR(20),                 -- Giới tính
        date_of_birth DATE,                  -- Ngày sinh
        email NVARCHAR(255) UNIQUE,          -- Email
        phone NVARCHAR(20),                  -- Số điện thoại
        address NVARCHAR(500),               -- Địa chỉ
        cccd NVARCHAR(20),                   -- Căn cước công dân
        class_code NVARCHAR(50),             -- Mã lớp
        major NVARCHAR(255),                 -- Ngành học
        enrollment_year INT,                 -- Năm nhập học
        status NVARCHAR(50) DEFAULT 'ACTIVE',  -- Trạng thái: ACTIVE, GRADUATED, SUSPENDED, DROPPED
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER
    );
    PRINT 'Created table: students';
END
GO

-- 1.5. Bảng courses (Môn học)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'courses')
BEGIN
    CREATE TABLE courses (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã môn học: IT101, MA101...
        name NVARCHAR(255) NOT NULL,         -- Tên môn học
        english_name NVARCHAR(255),          -- Tên tiếng Anh
        credits INT,                         -- Số tín chỉ
        course_type NVARCHAR(50),            -- Loại: Bắt buộc, Tự chọn
        department NVARCHAR(255),            -- Khoa phụ trách
        description NVARCHAR(1000),          -- Mô tả
        prerequisites NVARCHAR(500),         -- Môn học tiên quyết
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER
    );
    PRINT 'Created table: courses';
END
GO

-- =============================================
-- 2. TẠO CÁC BẢNG QUẢN LÝ HỌC TẬP
-- =============================================

-- 2.1. Bảng school_years (Năm học)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'school_years')
BEGIN
    CREATE TABLE school_years (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã năm học: 2025-2026
        name NVARCHAR(255) NOT NULL,         -- Tên năm học
        start_year INT,                      -- Năm bắt đầu: 2025
        end_year INT,                        -- Năm kết thúc: 2026
        start_date DATE,                     -- Ngày bắt đầu
        end_date DATE,                       -- Ngày kết thúc
        description NVARCHAR(500),           -- Mô tả
        is_current BIT DEFAULT 0,            -- Đánh dấu năm học hiện tại
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER
    );
    PRINT 'Created table: school_years';
END
GO

-- 2.2. Bảng semesters (Học kỳ)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'semesters')
BEGIN
    CREATE TABLE semesters (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã học kỳ: HK1-2025-2026
        name NVARCHAR(255) NOT NULL,         -- Tên học kỳ
        semester_order INT,                  -- Thứ tự học kỳ: 1, 2, 3 (hè)
        school_year_id UNIQUEIDENTIFIER NOT NULL,
        start_date DATE,                     -- Ngày bắt đầu
        end_date DATE,                       -- Ngày kết thúc
        registration_start DATE,             -- Ngày bắt đầu đăng ký
        registration_end DATE,               -- Ngày kết thúc đăng ký
        description NVARCHAR(500),           -- Mô tả
        is_current BIT DEFAULT 0,            -- Đánh dấu học kỳ hiện tại
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        FOREIGN KEY (school_year_id) REFERENCES school_years(id)
    );
    PRINT 'Created table: semesters';
END
GO

-- 2.3. Bảng course_sections (Lớp học phần)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'course_sections')
BEGIN
    CREATE TABLE course_sections (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50) NOT NULL UNIQUE,   -- Mã lớp học phần: IT101-01, IT101-02...
        name NVARCHAR(255),                  -- Tên lớp học phần
        course_id UNIQUEIDENTIFIER NOT NULL, -- Môn học
        semester_id UNIQUEIDENTIFIER NOT NULL, -- Học kỳ
        lecturer_id UNIQUEIDENTIFIER,        -- Giảng viên chính
        room_id UNIQUEIDENTIFIER,            -- Phòng học
        max_students INT DEFAULT 50,         -- Số sinh viên tối đa
        min_students INT DEFAULT 5,          -- Số sinh viên tối thiểu
        current_students INT DEFAULT 0,      -- Số sinh viên hiện tại
        class_type NVARCHAR(50),             -- Loại lớp: Lý thuyết, Thực hành, Lab
        status NVARCHAR(50) DEFAULT 'OPEN',  -- Trạng thái: OPEN, CLOSED, CANCELLED
        schedule_info NVARCHAR(500),         -- Thông tin lịch học
        note NVARCHAR(500),                  -- Ghi chú
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        FOREIGN KEY (course_id) REFERENCES courses(id),
        FOREIGN KEY (semester_id) REFERENCES semesters(id),
        FOREIGN KEY (lecturer_id) REFERENCES employees(id),
        FOREIGN KEY (room_id) REFERENCES rooms(id)
    );
    PRINT 'Created table: course_sections';
END
GO

-- 2.4. Bảng student_course_sections (Đăng ký học phần)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'student_course_sections')
BEGIN
    CREATE TABLE student_course_sections (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        student_id UNIQUEIDENTIFIER NOT NULL,
        course_section_id UNIQUEIDENTIFIER NOT NULL,
        status NVARCHAR(50) DEFAULT 'PENDING',  -- PENDING, APPROVED, REJECTED, CANCELLED
        registration_date DATETIME2 DEFAULT GETDATE(),
        approved_date DATETIME2,
        approved_by UNIQUEIDENTIFIER,
        grade NVARCHAR(10),                    -- Điểm: A, B, C, D, F
        grade_score DECIMAL(5,2),              -- Điểm số: 0-10
        note NVARCHAR(500),
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        FOREIGN KEY (student_id) REFERENCES students(id),
        FOREIGN KEY (course_section_id) REFERENCES course_sections(id),
        UNIQUE(student_id, course_section_id)  -- Không cho phép đăng ký trùng
    );
    PRINT 'Created table: student_course_sections';
END
GO

-- 2.5. Bảng lecturer_course_classes (Phân công giảng viên)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'lecturer_course_classes')
BEGIN
    CREATE TABLE lecturer_course_classes (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        employee_id UNIQUEIDENTIFIER NOT NULL,
        course_section_id UNIQUEIDENTIFIER NOT NULL,
        role NVARCHAR(50) DEFAULT 'LECTURER',  -- LECTURER, TA, CO_LECTURER
        teaching_hours INT,                    -- Số giờ giảng dạy
        is_primary BIT DEFAULT 0,              -- Giảng viên chính
        note NVARCHAR(500),
        is_active BIT DEFAULT 1,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        FOREIGN KEY (employee_id) REFERENCES employees(id),
        FOREIGN KEY (course_section_id) REFERENCES course_sections(id),
        UNIQUE(employee_id, course_section_id, role)
    );
    PRINT 'Created table: lecturer_course_classes';
END
GO

-- =============================================
-- 3. CHÈN DỮ LIỆU MẪU
-- =============================================

PRINT '========================================';
PRINT 'Inserting sample data...';
PRINT '========================================';

-- 3.1. Chèn tòa nhà (Buildings)
DECLARE @B1 UNIQUEIDENTIFIER = NEWID();
DECLARE @B2 UNIQUEIDENTIFIER = NEWID();
DECLARE @B3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO buildings (id, code, name, short_name, address, floors, description)
VALUES 
(@B1, 'A', N'Tòa nhà A', N'Tòa A', N'123 Đường ABC, Quận 1', 5, N'Tòa nhà chính - Khoa CNTT'),
(@B2, 'B', N'Tòa nhà B', N'Tòa B', N'456 Đường XYZ, Quận 1', 4, N'Tòa nhà khoa Kinh tế'),
(@B3, 'C', N'Tòa nhà C', N'Tòa C', N'789 Đường DEF, Quận 1', 3, N'Tòa nhà khoa Ngoại ngữ');

-- 3.2. Chèn phòng học (Rooms)
DECLARE @R1 UNIQUEIDENTIFIER = NEWID();
DECLARE @R2 UNIQUEIDENTIFIER = NEWID();
DECLARE @R3 UNIQUEIDENTIFIER = NEWID();
DECLARE @R4 UNIQUEIDENTIFIER = NEWID();
DECLARE @R5 UNIQUEIDENTIFIER = NEWID();

INSERT INTO rooms (id, code, name, building_id, floor, capacity, room_type, equipment)
VALUES
(@R1, 'A101', N'Phòng A101', @B1, 1, 50, N'Lý thuyết', N'Máy chiếu, Bảng trắng'),
(@R2, 'A102', N'Phòng A102', @B1, 1, 30, N'Thực hành', N'Máy tính, Máy chiếu'),
(@R3, 'A201', N'Phòng Lab 1', @B1, 2, 40, N'Lab', N'Máy tính, Internet'),
(@R4, 'B101', N'Phòng B101', @B2, 1, 60, N'Lý thuyết', N'Máy chiếu, Bảng trắng'),
(@R5, 'C101', N'Phòng C101', @B3, 1, 35, N'Thực hành', N'Máy chiếu, Bảng trắng');

-- 3.3. Chèn môn học (Courses)
DECLARE @C1 UNIQUEIDENTIFIER = NEWID();
DECLARE @C2 UNIQUEIDENTIFIER = NEWID();
DECLARE @C3 UNIQUEIDENTIFIER = NEWID();
DECLARE @C4 UNIQUEIDENTIFIER = NEWID();
DECLARE @C5 UNIQUEIDENTIFIER = NEWID();

INSERT INTO courses (id, code, name, english_name, credits, course_type, department, description)
VALUES
(@C1, 'IT101', N'Cơ sở dữ liệu', 'Database Fundamentals', 3, N'Bắt buộc', N'Khoa CNTT', N'Môn học về cơ sở dữ liệu quan hệ'),
(@C2, 'IT102', N'Lập trình Java nâng cao', 'Advanced Java Programming', 4, N'Bắt buộc', N'Khoa CNTT', N'Lập trình hướng đối tượng với Java'),
(@C3, 'IT103', N'Cấu trúc dữ liệu', 'Data Structures', 3, N'Bắt buộc', N'Khoa CNTT', N'Các cấu trúc dữ liệu cơ bản'),
(@C4, 'MA101', N'Toán rời rạc', 'Discrete Mathematics', 3, N'Bắt buộc', N'Khoa Toán', N'Toán học rời rạc cho CNTT'),
(@C5, 'EN101', N'Tiếng Anh chuyên ngành', 'English for IT', 2, N'Tự chọn', N'Khoa Ngoại ngữ', N'Tiếng Anh cho ngành CNTT');

-- 3.4. Chèn giảng viên (Employees)
DECLARE @E1 UNIQUEIDENTIFIER = NEWID();
DECLARE @E2 UNIQUEIDENTIFIER = NEWID();
DECLARE @E3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO employees (id, code, employee_type, first_name, last_name, gender, date_of_birth, email, phone, department, position, degree, hire_date)
VALUES
(@E1, 'GV001', N'Giảng viên', N'Nguyễn', N'Văn A', N'Nam', '1985-05-15', 'nguyenvana@university.edu', '0901234567', N'Khoa CNTT', N'Giảng viên', N'Tiến sĩ', '2015-09-01'),
(@E2, 'GV002', N'Giảng viên', N'Trần', N'Thị B', N'Nữ', '1988-08-20', 'tranthib@university.edu', '0902345678', N'Khoa CNTT', N'Giảng viên', N'Thạc sĩ', '2018-09-01'),
(@E3, 'GV003', N'Giảng viên', N'Lê', N'Văn C', N'Nam', '1982-03-10', 'levanc@university.edu', '0903456789', N'Khoa Toán', N'Trưởng khoa', N'Tiến sĩ', '2010-09-01');

-- 3.5. Chèn sinh viên (Students)
DECLARE @S1 UNIQUEIDENTIFIER = NEWID();
DECLARE @S2 UNIQUEIDENTIFIER = NEWID();
DECLARE @S3 UNIQUEIDENTIFIER = NEWID();
DECLARE @S4 UNIQUEIDENTIFIER = NEWID();
DECLARE @S5 UNIQUEIDENTIFIER = NEWID();

INSERT INTO students (id, code, first_name, last_name, gender, date_of_birth, email, phone, cccd, class_code, major, enrollment_year, status)
VALUES
(@S1, 'SV001', N'Phạm', N'Thị D', N'Nữ', '2005-07-20', 'phamthid@student.edu', '0911234567', '012345678901', 'CNTT25', N'Công nghệ thông tin', 2025, 'ACTIVE'),
(@S2, 'SV002', N'Nguyễn', N'Văn E', N'Nam', '2005-03-15', 'nguyenvane@student.edu', '0912345678', '012345678902', 'CNTT25', N'Công nghệ thông tin', 2025, 'ACTIVE'),
(@S3, 'SV003', N'Lê', N'Thị F', N'Nữ', '2004-11-10', 'lethif@student.edu', '0913456789', '012345678903', 'CNTT24', N'Công nghệ thông tin', 2024, 'ACTIVE'),
(@S4, 'SV004', N'Trần', N'Văn G', N'Nam', '2005-01-25', 'tranvang@student.edu', '0914567890', '012345678904', 'CNTT25', N'Công nghệ thông tin', 2025, 'ACTIVE'),
(@S5, 'SV005', N'Đỗ', N'Thị H', N'Nữ', '2004-09-30', 'dothih@student.edu', '0915678901', '012345678905', 'CNTT24', N'Công nghệ thông tin', 2024, 'ACTIVE');

-- 3.6. Chèn năm học (School Years)
DECLARE @SY1 UNIQUEIDENTIFIER = NEWID();
DECLARE @SY2 UNIQUEIDENTIFIER = NEWID();

INSERT INTO school_years (id, code, name, start_year, end_year, start_date, end_date, description, is_current)
VALUES
(@SY1, '2024-2025', N'Năm học 2024-2025', 2024, 2025, '2024-09-01', '2025-06-30', N'Năm học 2024-2025', 0),
(@SY2, '2025-2026', N'Năm học 2025-2026', 2025, 2026, '2025-09-01', '2026-06-30', N'Năm học hiện tại', 1);

-- 3.7. Chèn học kỳ (Semesters)
DECLARE @SM1 UNIQUEIDENTIFIER = NEWID();
DECLARE @SM2 UNIQUEIDENTIFIER = NEWID();
DECLARE @SM3 UNIQUEIDENTIFIER = NEWID();

INSERT INTO semesters (id, code, name, semester_order, school_year_id, start_date, end_date, registration_start, registration_end, is_current)
VALUES
(@SM1, 'HK1-2024-2025', N'Học kỳ 1 - 2024-2025', 1, @SY1, '2024-09-01', '2025-01-15', '2024-08-01', '2024-08-30', 0),
(@SM2, 'HK1-2025-2026', N'Học kỳ 1 - 2025-2026', 1, @SY2, '2025-09-01', '2026-01-15', '2025-08-01', '2025-08-30', 1),
(@SM3, 'HK2-2025-2026', N'Học kỳ 2 - 2025-2026', 2, @SY2, '2026-01-20', '2026-06-30', '2026-01-01', '2026-01-15', 0);

-- 3.8. Chèn lớp học phần (Course Sections)
DECLARE @CS1 UNIQUEIDENTIFIER = NEWID();
DECLARE @CS2 UNIQUEIDENTIFIER = NEWID();
DECLARE @CS3 UNIQUEIDENTIFIER = NEWID();
DECLARE @CS4 UNIQUEIDENTIFIER = NEWID();

INSERT INTO course_sections (id, code, name, course_id, semester_id, lecturer_id, room_id, max_students, min_students, current_students, class_type, status, schedule_info)
VALUES
(@CS1, 'IT101-01', N'Cơ sở dữ liệu - Lớp 01', @C1, @SM2, @E1, @R1, 50, 5, 25, N'Lý thuyết', 'OPEN', N'Thứ 2, Tiết 1-3'),
(@CS2, 'IT101-02', N'Cơ sở dữ liệu - Lớp 02', @C1, @SM2, @E2, @R2, 30, 5, 20, N'Thực hành', 'OPEN', N'Thứ 3, Tiết 4-6'),
(@CS3, 'IT102-01', N'Lập trình Java - Lớp 01', @C2, @SM2, @E1, @R3, 40, 5, 30, N'Lab', 'OPEN', N'Thứ 4, Tiết 1-3'),
(@CS4, 'MA101-01', N'Toán rời rạc - Lớp 01', @C4, @SM2, @E3, @R4, 60, 5, 45, N'Lý thuyết', 'OPEN', N'Thứ 5, Tiết 1-3');

-- 3.9. Chèn đăng ký học phần (Student Course Sections)
INSERT INTO student_course_sections (student_id, course_section_id, status, registration_date)
VALUES
(@S1, @CS1, 'APPROVED', GETDATE()),
(@S2, @CS1, 'APPROVED', GETDATE()),
(@S3, @CS1, 'APPROVED', GETDATE()),
(@S1, @CS3, 'APPROVED', GETDATE()),
(@S2, @CS3, 'APPROVED', GETDATE()),
(@S4, @CS3, 'PENDING', GETDATE()),
(@S3, @CS4, 'APPROVED', GETDATE()),
(@S5, @CS4, 'APPROVED', GETDATE());

-- 3.10. Chèn phân công giảng viên (Lecturer Course Classes)
INSERT INTO lecturer_course_classes (employee_id, course_section_id, role, teaching_hours, is_primary)
VALUES
(@E1, @CS1, 'LECTURER', 45, 1),
(@E2, @CS2, 'LECTURER', 30, 1),
(@E1, @CS3, 'LECTURER', 45, 1),
(@E2, @CS3, 'TA', 15, 0),
(@E3, @CS4, 'LECTURER', 45, 1);

PRINT '========================================';
PRINT 'Sample data inserted successfully!';
PRINT '========================================';
GO

-- =============================================
-- 4. TẠO INDEX ĐỂ TĂNG TỐC TRUY VẤN
-- =============================================
CREATE INDEX idx_rooms_building ON rooms(building_id);
CREATE INDEX idx_courses_code ON courses(code);
CREATE INDEX idx_employees_code ON employees(code);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_students_code ON students(code);
CREATE INDEX idx_students_email ON students(email);
CREATE INDEX idx_school_years_code ON school_years(code);
CREATE INDEX idx_semesters_school_year ON semesters(school_year_id);
CREATE INDEX idx_course_sections_course ON course_sections(course_id);
CREATE INDEX idx_course_sections_semester ON course_sections(semester_id);
CREATE INDEX idx_student_course_sections_student ON student_course_sections(student_id);
CREATE INDEX idx_student_course_sections_section ON student_course_sections(course_section_id);
CREATE INDEX idx_lecturer_course_classes_employee ON lecturer_course_classes(employee_id);
CREATE INDEX idx_lecturer_course_classes_section ON lecturer_course_classes(course_section_id);
GO

PRINT 'Indexes created successfully!';
GO