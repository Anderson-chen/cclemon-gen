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
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cclemon.entity.BaseEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="${tableName}")
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

}
