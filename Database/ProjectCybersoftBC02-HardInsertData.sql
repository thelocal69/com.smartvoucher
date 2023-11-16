INSERT INTO label (name) VALUES
('Ưu đãi đặc biệt'),
('Cuối tuần'),
('Chỉ có tại SmartVoucher'),
('Khỏe đẹp'),
('Công nghệ và Đời sống');

INSERT INTO category (category_code, name, status, created_by, created_at, banner_url)
VALUES
('cat_travel', 'Du lịch', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1pLSJ_K3i7MULkQf9qWGox_uYRyEvXVpg/view?usp=drivesdk'),
('cat_electronics', 'Điện tử', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1aclxsrK4N9nmwi75fKrjMLbRwugU9iZE/view?usp=drivesdk'),
('cat_fashion', 'Thời trang', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1mDZ52hL_fQxNCaft_Fb8FTzmBveLVdsm/view?usp=drivesdk'),
('cat_food', 'Ẩm thực', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1_iF6YjCNI-m_J_m280J0KsVoAMFkByo5/view?usp=drivesdk'),
('cat_books', 'Sách và Học nghệ thuật', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1I-EGotvTISIgdUrzx6klMHENa-oWBprR/view?usp=drivesdk'),
('cat_health', 'Sức khỏe và Làm đẹp', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1DWGu6FSpy0ZC8dKY_GkA1qV5973Xoi54/view?usp=drivesdk'),
('cat_home', 'Nội thất và Gia đình', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/14SNDRynnaWw8cfeZl0GdITf287rJr6H_/view?usp=drivesdk'),
('cat_technology', 'Công nghệ', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1kRUQplQ4y1gudv19AAbk3rj5pBD04SsG/view?usp=drivesdk'),
('cat_sports', 'Thể thao và Hoạt động ngoại ô', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1wds5PbgmbkNb3zcJF-IjrzEQp0m_AMmb/view?usp=drivesdk'),
('cat_game', 'Trò chơi', 1, 'ADMIN', NOW(), 'https://drive.google.com/file/d/1VcV1D6llffCHYTDOAZtlnZ0zSsBLO3V5/view?usp=drivesdk');

INSERT INTO discount_type (code, name, status, created_by, created_at)
VALUES 
('DT001', 'Percentage Off', 1, 'admin', '2023-11-11 00:00:00'),
('DT002', 'Fixed Amount Off', 1, 'admin', '2023-11-11 00:00:00'),
('DT003', 'Buy One Get One Free', 1, 'admin', '2023-11-11 00:00:00'),
('DT004', 'Free Shipping', 1, 'admin', '2023-11-11 00:00:00');