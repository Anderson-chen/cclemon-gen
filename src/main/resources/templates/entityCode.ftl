package ${groupId}.${lowerCaseTableName}.entity;

<#if hasBigDecimal >	
import java.math.BigDecimal;
</#if>
<#if hasDate >	
import java.sql.Date;
</#if>
<#if hasTime>	
import java.sql.Time;
</#if>
<#if hasTimestamp>	
import java.sql.Timestamp;
</#if>
<#if hasLocalDateTime>	
import java.time.LocalDateTime;
</#if>
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.cclemon.entity.BaseEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="${tableName}")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ${entityClassName}Entity extends BaseEntity {
    
    <#list metaDataDTOList as metaData>
	    <#if !metaData.isBaseField>
	/**
	 * Desc:${metaData.remark}
	 * Column Name:${metaData.columnName}, Column Type:${metaData.columnType}, Nullable:${metaData.getIsNullableStr()}
	 */
    private ${metaData.sqlToJavaType()} ${metaData.getFieldName()} ;
        </#if>
    </#list>

    public static ${entityClassName}Entity get${entityClassName}() {
        ${entityClassName}Entity entity = new ${entityClassName}Entity();
        <#list metaDataDTOList as metaData>
            <#if !metaData.isBaseField>
        entity.set${metaData.getFieldName()?cap_first}(${metaData.getDefaultValue()});
            </#if>
        </#list>
        return entity;
    }
}
