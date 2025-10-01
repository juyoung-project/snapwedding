package kr.or.wds.project.dto.response;

import kr.or.wds.project.common.enums.Status;
import kr.or.wds.project.entity.ExpertProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExpertProductResponse {

    private String productNm;
    private Long id;
    private Long thumbnailFileId;
    private String badge;
    private String price;
    private Integer order;
    private String productType;
    private Status status;
    private String description;
    private Integer durationHours;

    public static List<ExpertProductResponse> from(List<ExpertProductEntity> expertProductEntities) {
        return expertProductEntities.stream()
                .map(item -> new ExpertProductResponse(
                        item.getProductNm(),
                        item.getId(),
                        item.getThumbnailFileId(),
                        item.getBadge(),
                        item.getPrice(),
                        item.getOrder(),
                        item.getProductType(),
                        item.getStatus(),
                        item.getDescription(),
                        item.getDurationHours()
                        )).toList();
    }

}
