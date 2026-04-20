-- =============================================
-- Script tạo bảng và dữ liệu mẫu cho Quản lý Học phần
-- =============================================

USE quanlyhocphan;
GO

-- =============================================
-- 1. TẠO CÁC BẢNG
-- =============================================

-- 1.1. Bảng academic_years
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'academic_years')
BEGIN
    CREATE TABLE academic_years (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(50),
        name NVARCHAR(100),
        year NVARCHAR(20),
        description NVARCHAR(255),
        start_date DATE,
        end_date DATE,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: academic_years';
END

-- 1.2. Bảng school_years
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'school_years')
BEGIN
    CREATE TABLE school_years (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(100),
        name NVARCHAR(255),
        description NVARCHAR(255),
        note NVARCHAR(255),
        start_date DATE,
        end_date DATE,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: school_years';
END

-- 1.3. Bảng semesters
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'semesters')
BEGIN
    CREATE TABLE semesters (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code NVARCHAR(100),
        name NVARCHAR(255),
        school_year_id UNIQUEIDENTIFIER,
        school_year_name NVARCHAR(255),
        start_date DATE,
        end_date DATE,
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: semesters';
END

-- 1.4. Bảng course_sections
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'course_sections')
BEGIN
    CREATE TABLE course_sections (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        code VARCHAR(100),
        name NVARCHAR(255),
        course_id UNIQUEIDENTIFIER,
        semester_id UNIQUEIDENTIFIER,
        room_id UNIQUEIDENTIFIER,
        building_id UNIQUEIDENTIFIER,
        max_students INT,
        min_students INT,
        class_type NVARCHAR(255),
        status NVARCHAR(50),
        registration_start DATETIME2,
        registration_end DATETIME2,
        note NVARCHAR(255),
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: course_sections';
END

-- 1.5. Bảng student_course_sections
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'student_course_sections')
BEGIN
    CREATE TABLE student_course_sections (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        student_id UNIQUEIDENTIFIER,
        course_section_id UNIQUEIDENTIFIER,
        status VARCHAR(50),
        registered_at DATETIME DEFAULT GETDATE(),
        note NVARCHAR(255),
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: student_course_sections';
END

-- 1.6. Bảng lecturer_course_classes
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'lecturer_course_classes')
BEGIN
    CREATE TABLE lecturer_course_classes (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        employee_id UNIQUEIDENTIFIER,
        course_class_id UNIQUEIDENTIFIER,
        role NVARCHAR(50),
        created_at DATETIME2 DEFAULT GETDATE(),
        updated_at DATETIME2,
        created_by UNIQUEIDENTIFIER,
        updated_by UNIQUEIDENTIFIER,
        deleted_at DATETIME2,
        deleted_by UNIQUEIDENTIFIER,
        is_active BIT DEFAULT 1
    );
    PRINT 'Created table: lecturer_course_classes';
END

GO

-- =============================================
-- 2. CHÈN DỮ LIỆU MẪU
-- =============================================

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM lecturer_course_classes;
DELETE FROM student_course_sections;
DELETE FROM course_sections;
DELETE FROM semesters;
DELETE FROM school_years;
DELETE FROM academic_years;
GO

-- Khai báo biến để đồng bộ ID
DECLARE @AY_ID UNIQUEIDENTIFIER = NEWID();
DECLARE @SY_ID UNIQUEIDENTIFIER = NEWID();
DECLARE @SM_ID UNIQUEIDENTIFIER = NEWID();
DECLARE @CS_ID UNIQUEIDENTIFIER = NEWID();
DECLARE @CS_ID2 UNIQUEIDENTIFIER = NEWID();
DECLARE @ST_ID UNIQUEIDENTIFIER = NEWID();
DECLARE @EMP_ID UNIQUEIDENTIFIER = NEWID();

-- 2.1. Chèn Năm học (Academic Year)
INSERT INTO academic_years (id, code, name, year, description, start_date, end_date)
VALUES (@AY_ID, 'AY2526', N'Năm học 2025-2026', '2025-2026', N'Năm học 2025-2026', '2025-09-01', '2026-06-30');

-- 2.2. Chèn Năm học (School Year)
INSERT INTO school_years (id, code, name, description, start_date, end_date)
VALUES (@SY_ID, 'SY2526', N'Năm học 2025-2026', N'Năm học chính thức 2025-2026', '2025-09-01', '2026-06-30');

-- 2.3. Chèn Học kỳ (Semester)
INSERT INTO semesters (id, code, name, school_year_id, school_year_name, start_date, end_date)
VALUES (@SM_ID, 'HK1_2526', N'Học kỳ 1 - 2025-2026', @SY_ID, N'Năm học 2025-2026', '2025-09-01', '2026-01-15');

-- 2.4. Chèn Lớp học phần (Course Section)
INSERT INTO course_sections (id, code, name, semester_id, max_students, min_students, class_type, status, registration_start, registration_end)
VALUES 
(@CS_ID, 'IT_CS101', N'Cơ sở dữ liệu', @SM_ID, 40, 10, N'Lý thuyết', 'OPEN', '2025-08-01', '2025-08-30'),
(@CS_ID2, 'IT_CS102', N'Lập trình Java nâng cao', @SM_ID, 35, 10, N'Thực hành', 'OPEN', '2025-08-01', '2025-08-30');

-- 2.5. Chèn Sinh viên đăng ký lớp (Student Course Section)
INSERT INTO student_course_sections (id, student_id, course_section_id, status, registered_at)
VALUES 
(NEWID(), @ST_ID, @CS_ID, 'REGISTERED', GETDATE()),
(NEWID(), NEWID(), @CS_ID, 'REGISTERED', GETDATE()),
(NEWID(), NEWID(), @CS_ID, 'WAITING', GETDATE()),
(NEWID(), NEWID(), @CS_ID2, 'REGISTERED', GETDATE());

-- 2.6. Chèn Giảng viên dạy lớp (Lecturer Course Class)
INSERT INTO lecturer_course_classes (id, employee_id, course_class_id, role)
VALUES 
(NEWID(), @EMP_ID, @CS_ID, N'Giảng viên chính'),
(NEWID(), NEWID(), @CS_ID2, N'Giảng viên chính');

PRINT N'Đã chèn dữ liệu mẫu thành công!';
GO

-- =============================================
-- 3. CÁC CÂU LỆNH TRUY VẤN
-- =============================================

-- 3.1. Danh sách lớp học phần đầy đủ thông tin năm học/học kỳ
PRINT '=== 3.1. Danh sách lớp học phần ===';
SELECT 
    sy.name AS [Năm học],
    sm.name AS [Học kỳ],
    cs.code AS [Mã lớp],
    cs.name AS [Tên môn],
    cs.max_students AS [Sĩ số tối đa],
    cs.status AS [Trạng thái]
FROM course_sections cs
INNER JOIN semesters sm ON cs.semester_id = sm.id
INNER JOIN school_years sy ON sm.school_year_id = sy.id
WHERE cs.is_active = 1;

-- 3.2. Thống kê sĩ số thực tế của từng lớp
PRINT '=== 3.2. Thống kê sĩ số ===';
SELECT 
    cs.code AS [Mã lớp],
    cs.name AS [Tên môn],
    COUNT(scs.student_id) AS [Số SV đăng ký],
    cs.max_students AS [Chỉ tiêu],
    (cs.max_students - COUNT(scs.student_id)) AS [Còn trống]
FROM course_sections cs
LEFT JOIN student_course_sections scs ON cs.id = scs.course_section_id AND scs.is_active = 1
WHERE cs.is_active = 1
GROUP BY cs.code, cs.name, cs.max_students;

-- 3.3. Danh sách phân công giảng dạy
PRINT '=== 3.3. Phân công giảng dạy ===';
SELECT 
    cs.code AS [Mã lớp],
    cs.name AS [Tên môn],
    lcc.employee_id AS [Mã GV],
    lcc.role AS [Vai trò]
FROM lecturer_course_classes lcc
INNER JOIN course_sections cs ON lcc.course_class_id = cs.id
WHERE lcc.is_active = 1;

-- 3.4. Thống kê tổng quan
PRINT '=== 3.4. Thống kê tổng quan ===';
SELECT 
    (SELECT COUNT(*) FROM school_years WHERE is_active = 1) AS [Tổng năm học],
    (SELECT COUNT(*) FROM semesters WHERE is_active = 1) AS [Tổng học kỳ],
    (SELECT COUNT(*) FROM course_sections WHERE is_active = 1) AS [Tổng lớp học phần],
    (SELECT COUNT(*) FROM lecturer_course_classes WHERE is_active = 1) AS [Tổng phân công];

GO

PRINT N'Hoàn tất cài đặt database!';