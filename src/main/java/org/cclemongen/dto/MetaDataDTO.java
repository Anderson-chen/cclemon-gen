package org.cclemongen.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class MetaDataDTO {

    String columnName;
    String columnType;
    int columnSize;
    String remark;
    String isNullable;
    Boolean isBaseField;

    public static String getFieldName(String input) {

        StringBuilder sb = new StringBuilder();

        String[] split = input.split("_");

        for (String str : split) {

            sb.append(StringUtils.capitalize(str.toLowerCase()));
        }

        return decapitalize(sb.toString());
    }

    public static String decapitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        char[] chars = input.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public String getFieldName() {

        StringBuilder sb = new StringBuilder();

        String[] split = this.columnName.split("_");

        for (String str : split) {

            sb.append(StringUtils.capitalize(str.toLowerCase()));
        }

        return decapitalize(sb.toString());
    }

    public String getIsNullableStr() {
        return "Yes".equals(this.isNullable) ? "Y" : "N";
    }

    public String sqlToJavaType() {
        return switch (this.columnType) {
            case "INT", "INTEGER", "BIGINT", "SMALLINT", "TINYINT", "FLOAT", "REAL", "DOUBLE", "NUMERIC", "DECIMAL" ->
                    "BigDecimal";
            case "BOOLEAN", "BIT" -> "Boolean";
            case "CHAR", "VARCHAR", "LONGVARCHAR", "DATE", "TIME", "TIMESTAMP", "DATETIME" -> "String";
            default -> "String"; // 預設為字串型態
        };
    }

    public String getDefaultValue() {
        return switch (this.columnType) {
            case "INT", "INTEGER", "BIGINT", "SMALLINT", "TINYINT", "FLOAT", "REAL", "DOUBLE", "NUMERIC", "DECIMAL", "BOOLEAN", "BIT" ->
                    "new BigDecimal(0)";
            case "CHAR", "VARCHAR", "LONGVARCHAR", "DATE", "TIME", "TIMESTAMP", "DATETIME" -> "\"\"";
            default -> "\"\""; // 預設為字串型態
        };
    }
}
