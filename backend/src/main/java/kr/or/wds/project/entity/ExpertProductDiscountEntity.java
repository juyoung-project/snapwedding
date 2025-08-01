package kr.or.wds.project.entity;       

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import kr.or.wds.project.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "expert_product_discounts")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertProductDiscountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expert_product_id")
    private Long expertProductId;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private String discountValue;

    @Column(name = "min_price")
    private String minPrice;

    @Column(name = "max_discount_amount")
    private String maxDiscountAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    
}