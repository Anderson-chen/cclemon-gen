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
    Boolean isBaseField;

    public String getFieldName() {

        StringBuilder sb = new StringBuilder();

        String[] split = this.columnName.split("_");

        for (String str : split) {

            sb.append(StringUtils.capitalize(str.toLowerCase()));
        }

        return decapitalize(sb.toString());
    }

    public static String getFieldName(String input) {

        StringBuilder sb = new StringBuilder();

        String[] split = input.split("_");

        for (String str : split) {

            sb.append(StringUtils.capitalize(str.toLowerCase()));
        }

        return decapitalize(sb.toString());
    }

    public String getIsNullableStr() {
        return "Yes".equals(this.isNullable) ? "Y" : "N";
    }

    public static String decapitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        char[] chars = input.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public String sqlToJavaType() {
        switch (this.columnType) {
            case "INT":
            case "INTEGER":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "SMALLINT":
                return "Short";
            case "TINYINT":
                return "Byte";
            case "FLOAT":
            case "REAL":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "NUMERIC":
            case "DECIMAL":
                return "BigDecimal";
            case "BOOLEAN":
            case "BIT":
                return "Boolean";
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                return "String";
            case "DATE":
                return "Date";
            case "TIME":
                return "Time";
            case "TIMESTAMP":
                return "Timestamp";
            case "DATETIME":
                return "LocalDateTime";
            default:
                return "String"; // 預設為字串型態
        }
    }

}
