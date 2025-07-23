package kr.or.wds.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;
}
