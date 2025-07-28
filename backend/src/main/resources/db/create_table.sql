CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255),
  name VARCHAR(100) NOT NULL,
  nickname VARCHAR(100),
  phone VARCHAR(20) NULL,
  birth_date DATE,
  wedding_date DATE,
  profile_image VARCHAR(500),
  status VARCHAR(20) DEFAULT 'active',
  role VARCHAR(20) DEFAULT 'user',
  expert_status VARCHAR(20),
  expert_service_type VARCHAR(50) NULL,
  expert_region_id BIGINT NULL,
  oauth_provider VARCHAR(20) DEFAULT 'local',
  oauth_id VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  UNIQUE KEY unique_oauth (oauth_provider, oauth_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE regions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  parent_id BIGINT, -- FK 제약조건 없이 단순 컬럼
  level INT DEFAULT 1, -- 1: 시/도, 2: 구/군, 3: 동/읍/면
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_regions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  region_id BIGINT NOT NULL,
  priority TINYINT NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  UNIQUE KEY unique_user_region (user_id, region_id),
  INDEX idx_user_id (user_id),
  INDEX idx_region_id (region_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE expert_applications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  service_type VARCHAR(20) NOT NULL,
  region_id BIGINT NOT NULL, -- regions 테이블과 연동
  intro TEXT,
  portfolio_urls JSON,
  experience TEXT,
  status VARCHAR(20) DEFAULT 'pending',
  review_note TEXT,
  reviewer_id BIGINT NULL,
  applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  reviewed_at TIMESTAMP NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  INDEX idx_user_id (user_id),
  INDEX idx_reviewer_id (reviewer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE experts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT UNIQUE NOT NULL,
  application_id BIGINT NOT NULL,
  service_type VARCHAR(20) NOT NULL,
  region_id BIGINT NOT NULL,
  intro TEXT,
  portfolio_urls JSON,
  available_dates JSON,
  status VARCHAR(20) DEFAULT 'active',
  approved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE expert_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_nm VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    `order` INT DEFAULT 0, -- order는 MySQL 예약어라 백틱 필요
    expert_id BIGINT NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'active',
    description TEXT,
    duration_hours INT, -- 서비스 소요 시간 (예: 2시간, 4시간)
    thumbnail_image VARCHAR(500),
    detail_images JSON, -- 여러 이미지 URL 저장
    is_featured CHAR(1) DEFAULT 'N', -- 추천 상품 여부
    view_count INT DEFAULT 0, -- 조회수
    booking_count INT DEFAULT 0, -- 예약 수
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_user_id BIGINT,
    update_user_id BIGINT,
    del_yn CHAR(1) DEFAULT 'N',
    INDEX idx_expert_id (expert_id),
    INDEX idx_product_type (product_type),
    INDEX idx_status (status),
    INDEX idx_order (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE expert_product_discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    expert_product_id BIGINT NOT NULL,
    discount_name VARCHAR(100) NOT NULL, -- "신규 가입 할인", "시즌 할인" 등
    discount_type VARCHAR(20) NOT NULL,
    discount_value VARCHAR(1000) , -- percentage면 10.00 (10%), fixed면 50000 (5만원)
    min_price VARCHAR(1000) NULL, -- 최소 주문 금액 (할인 적용 조건)
    max_discount_amount VARCHAR(1000) NULL, -- 최대 할인 금액 (percentage 할인 시)
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'active',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_user_id BIGINT,
    update_user_id BIGINT,
    del_yn CHAR(1) DEFAULT 'N',
    INDEX idx_product_id (expert_product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;