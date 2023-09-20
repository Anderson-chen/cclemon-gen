package org.cclemongen.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeMakerGenDTO {

    private String schema;
    private String tableName;
    private String destination;
    private String groupId;
    private List<MetaDataDTO> MetaDataDTOList;
    @Builder.Default
    private Boolean hasTime = false;
    @Builder.Default
    private Boolean hasBigDecimal = false;
    @Builder.Default
    private Boolean hasTimestamp = false;
    @Builder.Default
    private Boolean hasDate = false;
    @Builder.Default
    private Boolean hasLocalDateTime = false;

}
