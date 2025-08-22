package kr.or.wds.project.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Table(name = "file_masters")
@AllArgsConstructor
@RequiredArgsConstructor
public class FileMasterEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @Column(name = "file_path")
    @Schema(description = "파일 경로")
    private String filePath;

    @Column(name = "file_extension")
    @Schema(description = "파일 확장자")
    private String fileExtension;

    @Column(name = "file_nm")
    @Schema(description = "파일 원본명")
    private String fileNm;

    @Column(name = "file_store_nm")
    @Schema(description = "저장용_파일명_UUID")
    private String fileStoreNm;

    @Column(name = "file_size")
    @Schema(description = "파일 크기")
    private long fileSize;

    @Column(name = "file_mime_type")
    @Schema(description = "MIME 타입")
    private String fileMimeType;

}
