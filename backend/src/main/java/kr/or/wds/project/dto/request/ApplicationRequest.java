package kr.or.wds.project.dto.request;

import kr.or.wds.project.common.ServiceType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "userId is required")
    @Schema(description = "유저 ID")
    private Long userId;

    @NotNull(message = "serviceType is required")
    @Schema(description = "서비스 타입")
    private ServiceType serviceType;

    @NotNull(message = "regionId is required")
    @Schema(description = "지역")
    private Long regionId;

    @NotNull(message = "intro is required")
    @Schema(description = "소개")
    private String intro;

    @NotNull(message = "portfolioUrls is required")
    @Schema(description = "포트폴리오 URL")
    private String portfolioUrls;

    @NotNull(message = "experience is required")
    @Schema(description = "경력")
    private String experience; 


}
