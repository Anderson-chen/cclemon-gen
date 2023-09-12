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

    private BigDecimal ${metaData.columnName} 
    private BigDecimal dayFat;
    private BigDecimal dayVFat;
    private String scaleTime;
    private String brekkieTime;
    private String lunchTime;
    private String dinnerTime;
    private Float water;
    private Integer poopCount;
    private BigDecimal nightWeight = BigDecimal.ZERO;
    private BigDecimal nightFat;
    private BigDecimal nightVFat;
    private String bedTime;
    private String remark;
    private String remotePath;
    
    <#list metaDataDTOList as metaData>
	/**
	 * Desc:${metaData.remark}
	 * Column Name: ,Column Type:,  
	 */
    private ${metaData.columnType} ${metaData.columnName} ;
    </#list>

}
