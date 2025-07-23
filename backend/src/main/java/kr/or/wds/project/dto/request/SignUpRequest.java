package kr.or.wds.project.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    
    private String password;
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private LocalDate birthDate;
    private LocalDate weddingDate;
    
    
}
