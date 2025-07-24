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
  price INT NOT NULL,
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

CREATE TABLE payments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  amount INT NOT NULL,
  commission_amount INT NOT NULL,
  payment_method ENUM('kakao', 'toss', 'naver', 'card') NOT NULL,
  status ENUM('pending', 'paid', 'refunded', 'failed') DEFAULT 'pending',
  paid_at TIMESTAMP NULL,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_booking_id (booking_id),
  INDEX idx_status (status),
  INDEX idx_paid_at (paid_at),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE reviews (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  expert_id BIGINT NOT NULL,
  rating TINYINT NOT NULL,
  comment TEXT,
  images JSON,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  UNIQUE KEY unique_booking_review (booking_id),
  INDEX idx_user_id (user_id),
  INDEX idx_expert_id (expert_id),
  INDEX idx_rating (rating),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (expert_id) REFERENCES experts(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE invitations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  template VARCHAR(50) NOT NULL,
  info JSON NOT NULL,
  url VARCHAR(255) UNIQUE NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_user_id (user_id),
  INDEX idx_url (url),
  INDEX idx_is_active (is_active),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE albums (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  invitation_id BIGINT NOT NULL,
  media_urls JSON NOT NULL,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_invitation_id (invitation_id),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (invitation_id) REFERENCES invitations(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;