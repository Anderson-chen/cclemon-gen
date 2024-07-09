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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

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

       public String insertSQL = """
            INSERT
                INTO
             ${tableName}
             (
             <#list metaDataDTOList as metaData>
                 ${metaData.columnName}<#if metaData_has_next>, </#if>
             </#list>
             )
                VALUES
             (
           <#list metaDataDTOList as metaData>
                 :${metaData.getFieldName()}<#if metaData_has_next>, </#if>
           </#list>
              )
        """;

        public void insert(NamedParameterJdbcTemplate template) {
            template.update(insertSQL,new BeanPropertySqlParameterSource(this));
        }


        public List<${entityClassName}Entity> query(NamedParameterJdbcTemplate template, ${entityClassName}Entity entity) {
                StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
                queryBuilder.append(tableName).append(" WHERE 1=1 ");

                MapSqlParameterSource params = new MapSqlParameterSource();

                <#list metaDataDTOList as metaData>
                    <#if !metaData.isBaseField>
                    // Check if the field in the entity is not null before adding it to the query
                    if (entity.get${metaData.getFieldName()?cap_first}() != null) {
                        queryBuilder.append(" AND ${metaData.columnName} = :${metaData.getFieldName()} ");
                        params.addValue("${metaData.getFieldName()}", entity.get${metaData.getFieldName()?cap_first}());
                    }
                    </#if>
                </#list>

                // Execute the query and return the result
                return template.query(queryBuilder.toString(), params, (rs, rowNum) -> {
                    ${entityClassName}Entity resultEntity = new ${entityClassName}Entity();
                    <#list metaDataDTOList as metaData>
                        <#if !metaData.isBaseField>
                    resultEntity.set${metaData.getFieldName()?cap_first}(rs.get${metaData.sqlToJavaType()}("${metaData.columnName}"));
                        </#if>
                    </#list>
                    return resultEntity;
                });
            }


}
