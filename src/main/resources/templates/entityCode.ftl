package ${groupId}.${entityClassName}.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cclemon.entity.BaseEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table
public class ${entityClassName}Entity extends BaseEntity {
    
    <#list metaDataDTOList as metaData>
	/**
	 * Desc:${metaData.remark}
	 * Column Name:${metaData.columnName}, Column Type:${metaData.columnType}, Nullable:${metaData.getIsNullableStr()}
	 */
    private ${metaData.sqlToJavaType()} ${metaData.getFieldName()} ;
    
    </#list>

}
