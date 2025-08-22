package kr.or.wds.project.common.records;

import kr.or.wds.project.common.enums.FileUploadDomainType;

import java.util.List;

public record FileAfterContext(
        FileUploadDomainType domain,
        Long aggregateId,
        List<Long> fileIds,
        Long uploadUserId
) {
}