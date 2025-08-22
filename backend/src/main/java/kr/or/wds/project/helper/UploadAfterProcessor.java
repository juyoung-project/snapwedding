package kr.or.wds.project.helper;

import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.records.FileAfterContext;

public interface UploadAfterProcessor {

    boolean supports(FileUploadDomainType domain);
    String process(FileAfterContext context);


}
