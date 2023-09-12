package org.cclemongen.dto;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaDataDTO {

    String columnName;
    String columnType;
    int columnSize;
    String remark;
    String isNullable;

    public String getFieldName() {

        StringBuilder sb = new StringBuilder();

        String[] split = this.columnName.split("_");

        for (String str : split) {

            sb.append(StringUtils.capitalize(str.toUpperCase()));
        }

        return sb.toString();
    }

    public String getIsNullableStr() {
        return "Yes".equals(this.isNullable) ? "Y" : "N";
    }

    

}
