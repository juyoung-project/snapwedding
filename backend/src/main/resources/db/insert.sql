INSERT INTO regions (name, parent_id, level) VALUES
-- 시/도 레벨 (parent_id: NULL, level: 1)
('서울특별시', NULL, 1),
('부산광역시', NULL, 1),
('대구광역시', NULL, 1),
('인천광역시', NULL, 1),
('광주광역시', NULL, 1),
('대전광역시', NULL, 1),
('울산광역시', NULL, 1),
('세종특별자치시', NULL, 1),
('경기도', NULL, 1),
('강원도', NULL, 1),
('충청북도', NULL, 1),
('충청남도', NULL, 1),
('전라북도', NULL, 1),
('전라남도', NULL, 1),
('경상북도', NULL, 1),
('경상남도', NULL, 1),
('제주특별자치도', NULL, 1);







INSERT INTO portfolio_categories 
(category_name, category_code, parent_id, depth, description, display_order, is_required, status) 
VALUES 
-- 기본적인 웨딩 서비스 카테고리
('스냅촬영', 'SNAP', NULL, 1, '웨딩 스냅 촬영 서비스', 1, 'Y', 'ACTIVE'),
('본식영상', 'VIDEO', NULL, 1, '본식 웨딩 영상 서비스', 2, 'Y', 'ACTIVE'),
('본식스냅', 'CEREMONY_SNAP', NULL, 1, '본식 스냅 촬영 서비스', 3, 'Y', 'ACTIVE'),
('드론촬영', 'DRONE', NULL, 1, '드론 촬영 서비스', 4, 'N', 'ACTIVE'),
('축가', 'SONG', NULL, 1, '웨딩 축가 서비스', 5, 'N', 'ACTIVE'),
('사회자', 'MC', NULL, 1, '웨딩 사회자 서비스', 6, 'N', 'ACTIVE');