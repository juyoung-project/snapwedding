package kr.or.wds.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data   
public class ExpertProductOrderRequest {

    @NotNull(message = "id is required")
    @Schema(description = "ID")
    private Long id;

    @NotNull(message = "order is required")
    @Schema(description = "순서")
    private Integer order;

}
