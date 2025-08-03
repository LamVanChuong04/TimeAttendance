use timeattendance;
select * from job;
INSERT INTO job (name) VALUES
('Nhân viên IT'),
('Kế toán viên'),
('Trưởng phòng nhân sự'),
('Thư ký'),
('Bảo vệ'),
('Nhân viên bán hàng'),
('Lái xe'),
('Giám đốc kỹ thuật'),
('Thực tập sinh'),
('Chuyên viên marketing');

select * from division;
INSERT INTO division (name) VALUES
('Phòng Kỹ thuật'),
('Phòng Nhân sự'),
('Phòng Kế toán'),
('Phòng Kinh doanh'),
('Phòng Marketing'),
('Phòng Hành chính'),
('Phòng IT'),
('Phòng Sản xuất');

select * from department;
INSERT INTO department (name) VALUES
('Bộ phận phát triển phần mềm'),
('Bộ phận bảo trì'),
('Bộ phận chăm sóc khách hàng'),
('Bộ phận tuyển dụng'),
('Bộ phận đào tạo'),
('Bộ phận kế hoạch'),
('Bộ phận kỹ thuật'),
('Bộ phận kiểm thử');

select * from position;
INSERT INTO position (name) VALUES
('Giám đốc'),
('Phó giám đốc'),
('Trưởng phòng'),
('Phó phòng'),
('Trưởng nhóm'),
('Nhân viên chính thức'),
('Nhân viên thử việc'),
('Thực tập sinh'),
('Kỹ sư phần mềm'),
('Kế toán');

select * from shift_type;
INSERT INTO shift_type (name, coefficient) VALUES
('Ca sáng', 1.0),
('Ca chiều', 1.0),
('Ca tối', 1.2),
('Ca đêm', 1.5),
('Ca hành chính', 1.0),
('Ca gãy', 1.1),
('Ca tăng cường', 1.3),
('Ca cuối tuần', 1.5),
('Ca ngày lễ', 2.0),
('Ca khẩn cấp', 1.8);
select * from employee;
select * from overtime;
INSERT INTO overtime (year, month, day, hours, employee_id, shift_type_id) VALUES
(2025, 7, 10, 2, 3, 1),
(2025, 7, 11, 3, 5, 2),
(2025, 7, 12, 1, 7, 3),
(2025, 7, 13, 4, 3, 4),
(2025, 7, 14, 2, 5, 5),
(2025, 7, 15, 3, 7, 2),
(2025, 7, 16, 1, 5, 3),
(2025, 7, 17, 2, 3, 1),
(2025, 7, 18, 3, 3, 4),
(2025, 7, 19, 2, 7, 5);
INSERT INTO allowance (name, amount) VALUES 
('Ngày Lễ', 500000),
('Chủ nhật', 300000);

INSERT INTO employee_allowance (amount, content, allowance_id, employee_id)
VALUES 
(200000, 'Phụ cấp ngày Lễ', 1, 5),
(100000, 'Phụ cấp làm ngày chủ nhật', 2, 7);


select o.day, o.hours, o.month, s.coefficient, s.name
from overtime o
left join shift_type s on o.shift_type_id = s.id;
SELECT * FROM salary WHERE employee_id = 2 AND month = 7 AND year = 2025;
