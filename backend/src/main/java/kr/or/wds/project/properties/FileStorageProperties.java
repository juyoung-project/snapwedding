package kr.or.wds.project.properties;

import kr.or.wds.project.common.enums.StorageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {
    private StorageType provider = StorageType.LOCAL;
    private String path;
}
