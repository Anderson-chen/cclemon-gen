package org.cclemon.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table
@DynamicUpdate
@JsonRootName(value = "result")
@JsonIgnoreProperties(value = { "_links" }, allowGetters = true)
public class ${entityClassName} extends BaseEntity {
    
    <#list metaDataDTOList as metaData>
	/**
	 * Desc:${metaData.remark}
	 * Column Name:${metaData.columnName}, Column Type:${metaData.columnType}, Nullable:${metaData.getIsNullableStr()}
	 */
    private ${metaData.sqlToJavaType()} ${metaData.getFieldName()} ;
    
    </#list>

}
