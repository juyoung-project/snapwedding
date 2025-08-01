package kr.or.wds.project.entity;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.or.wds.project.common.enums.Status;
import kr.or.wds.project.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users",
       indexes = {
           @Index(name = "idx_user_email", columnList = "email"),
           @Index(name = "idx_user_nickname", columnList = "nickname"),
           @Index(name = "idx_user_oauth", columnList = "oauth_provider, oauth_id")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "user_email_key", columnNames = "email"),
           @UniqueConstraint(name = "user_nickname_key", columnNames = "nickname"),
           @UniqueConstraint(name = "user_oauth_key", columnNames = {"oauth_provider", "oauth_id"})
       }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "회원 아이디")
    @Column(name = "id")
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    @Schema(description = "회원 이메일")
    private String email;

    @Column(length = 255)
    @Schema(description = "회원 비밀번호 (OAuth 사용자는 NULL 가능)")
    private String password;

    @Column(length = 100, nullable = false)
    @Schema(description = "회원 이름")
    private String name;

    @Column(length = 100)
    @Schema(description = "회원 닉네임")
    private String nickname;

    @Column(length = 20)
    @Schema(description = "회원 연락처")
    private String phone;

    @Column(name = "birth_date")
    @Schema(description = "회원 생년월일")
    private LocalDate birthDate;

    @Column(name = "wedding_date")
    @Schema(description = "결혼 예정일")
    private LocalDate weddingDate;

    @Column(name = "profile_image", length = 500)
    @Schema(description = "프로필 이미지 URL")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Schema(description = "회원 상태")
    private Status status = Status.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Schema(description = "회원 역할")
    private UserRole role = UserRole.USER;

    @Column(name = "oauth_provider", length = 20, nullable = false)
    @Schema(description = "OAuth 제공자")
    private String oauthProvider;

    @Column(name = "oauth_id", length = 255)
    @Schema(description = "OAuth 제공자의 고유 ID")
    private String oauthId;


    
}