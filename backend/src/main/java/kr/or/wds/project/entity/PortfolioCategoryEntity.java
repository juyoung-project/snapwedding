package kr.or.wds.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import kr.or.wds.project.common.enums.Status;

@Entity
@Table(name = "portfolio_categories")
public class PortfolioCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_required")
    private String isRequired;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)    
    private Status status;
}
