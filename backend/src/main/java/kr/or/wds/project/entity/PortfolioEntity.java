package kr.or.wds.project.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.or.wds.project.common.enums.PortfolioStatus;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)
    private ExpertEntity expert;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "category_id", nullable = false)
    private PortfolioCategoryEntity category;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<PortfolioRegionEntity> regions;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "tags")
    private String tags;         

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "display_order")
    private String displayOrder;

    @Column(name = "is_featured")
    private String isFeatured;

    @Column(name = "is_public")
    private String isPublic;    

    @Column(name = "shooting_date")
    private String shootingDate;


    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "save_count")
    private Long saveCount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PortfolioStatus status;

}
