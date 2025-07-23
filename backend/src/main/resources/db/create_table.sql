CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `wedding_date` date DEFAULT NULL,
  `profile_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','SUSPENDED') COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ADMIN','USER') COLLATE utf8mb4_unicode_ci NOT NULL,
  `oauth_provider` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `oauth_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint DEFAULT NULL,
  `update_user_id` bigint DEFAULT NULL,
  `del_yn` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `user_email_key` (`email`),
  UNIQUE KEY `unique_oauth` (`oauth_provider`,`oauth_id`),
  UNIQUE KEY `user_nickname_key` (`nickname`),
  UNIQUE KEY `user_oauth_key` (`oauth_provider`,`oauth_id`),
  KEY `idx_role` (`role`),
  KEY `idx_user_email` (`email`),
  KEY `idx_user_nickname` (`nickname`),
  KEY `idx_user_oauth` (`oauth_provider`,`oauth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci


CREATE TABLE regions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) UNIQUE NOT NULL,
  name VARCHAR(100) NOT NULL,
  parent_id BIGINT NULL,
  level TINYINT NOT NULL,
  sort_order INT DEFAULT 0,
  is_active BOOLEAN DEFAULT TRUE,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_parent_id (parent_id),
  INDEX idx_level (level),
  INDEX idx_sort_order (sort_order),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (parent_id) REFERENCES regions(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE user_regions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  region_id BIGINT NOT NULL,
  priority TINYINT NOT NULL,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  UNIQUE KEY unique_user_region (user_id, region_id),
  INDEX idx_user_id (user_id),
  INDEX idx_region_id (region_id),
  INDEX idx_priority (priority),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (region_id) REFERENCES regions(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE expert_applications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  service_type ENUM('snap_expert', 'mc_expert', 'singer_expert', 'photographer_expert') NOT NULL,
  region VARCHAR(100) NOT NULL,
  price INT NOT NULL,
  intro TEXT,
  portfolio_urls JSON,
  experience TEXT,
  status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
  review_note TEXT,
  reviewer_id BIGINT NULL,
  applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  reviewed_at TIMESTAMP NULL,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_user_id (user_id),
  INDEX idx_status (status),
  INDEX idx_applied_at (applied_at),
  INDEX idx_reviewer_id (reviewer_id),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (reviewer_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE experts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT UNIQUE NOT NULL,
  application_id BIGINT NOT NULL,
  service_type ENUM('snap_expert', 'mc_expert', 'singer_expert', 'photographer_expert') NOT NULL,
  region VARCHAR(100) NOT NULL,
  price INT NOT NULL,
  intro TEXT,
  portfolio_urls JSON,
  available_dates JSON,
  status ENUM('active', 'suspended', 'inactive') DEFAULT 'active',
  approved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_user_id (user_id),
  INDEX idx_service_type (service_type),
  INDEX idx_region (region),
  INDEX idx_status (status),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (application_id) REFERENCES expert_applications(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE experts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT UNIQUE NOT NULL,
  application_id BIGINT NOT NULL,
  service_type ENUM('snap_expert', 'mc_expert', 'singer_expert', 'photographer_expert') NOT NULL,
  region VARCHAR(100) NOT NULL,
  price INT NOT NULL,
  intro TEXT,
  portfolio_urls JSON,
  available_dates JSON,
  status ENUM('active', 'suspended', 'inactive') DEFAULT 'active',
  approved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  -- 공통 컬럼
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_user_id BIGINT,
  update_user_id BIGINT,
  del_yn CHAR(1) DEFAULT 'N',
  
  INDEX idx_user_id (user_id),
  INDEX idx_service_type (service_type),
  INDEX idx_region (region),
  INDEX idx_status (status),
  INDEX idx_del_yn (del_yn),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (application_id) REFERENCES expert_applications(id)
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