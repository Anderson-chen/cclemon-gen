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
public class Body extends BaseEntity {
    
	/**
	 * Desc:PK
	 * Column Name:id, Column Type:BIGINT, Nullable:N
	 */
    private Long id ;
    
	/**
	 * Desc:last_modified_time
	 * Column Name:last_modified_time, Column Type:DATETIME, Nullable:N
	 */
    private String lastModifiedTime ;
    
	/**
	 * Desc:
	 * Column Name:create_time, Column Type:DATETIME, Nullable:N
	 */
    private String createTime ;
    
	/**
	 * Desc:
	 * Column Name:create_user_id, Column Type:INT, Nullable:N
	 */
    private String createUserId ;
    
	/**
	 * Desc:
	 * Column Name:deleted, Column Type:BIT, Nullable:N
	 */
    private String deleted ;
    
	/**
	 * Desc:
	 * Column Name:last_modified_user_id, Column Type:INT, Nullable:N
	 */
    private String lastModifiedUserId ;
    
	/**
	 * Desc:
	 * Column Name:version, Column Type:INT, Nullable:N
	 */
    private String version ;
    
	/**
	 * Desc:
	 * Column Name:bed_time, Column Type:VARCHAR, Nullable:N
	 */
    private String bedTime ;
    
	/**
	 * Desc:
	 * Column Name:brekkie_time, Column Type:VARCHAR, Nullable:N
	 */
    private String brekkieTime ;
    
	/**
	 * Desc:
	 * Column Name:day_fat, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal dayFat ;
    
	/**
	 * Desc:
	 * Column Name:dayvfat, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal dayvfat ;
    
	/**
	 * Desc:
	 * Column Name:day_weight, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal dayWeight ;
    
	/**
	 * Desc:
	 * Column Name:dinner_time, Column Type:VARCHAR, Nullable:N
	 */
    private String dinnerTime ;
    
	/**
	 * Desc:
	 * Column Name:lunch_time, Column Type:VARCHAR, Nullable:N
	 */
    private String lunchTime ;
    
	/**
	 * Desc:
	 * Column Name:night_fat, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal nightFat ;
    
	/**
	 * Desc:
	 * Column Name:nightvfat, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal nightvfat ;
    
	/**
	 * Desc:
	 * Column Name:night_weight, Column Type:DECIMAL, Nullable:N
	 */
    private BigDecimal nightWeight ;
    
	/**
	 * Desc:
	 * Column Name:poop_count, Column Type:INT, Nullable:N
	 */
    private String poopCount ;
    
	/**
	 * Desc:
	 * Column Name:remark, Column Type:VARCHAR, Nullable:N
	 */
    private String remark ;
    
	/**
	 * Desc:
	 * Column Name:remote_path, Column Type:VARCHAR, Nullable:N
	 */
    private String remotePath ;
    
	/**
	 * Desc:
	 * Column Name:scale_time, Column Type:VARCHAR, Nullable:N
	 */
    private String scaleTime ;
    
	/**
	 * Desc:
	 * Column Name:water, Column Type:FLOAT, Nullable:N
	 */
    private Float water ;
    

}
