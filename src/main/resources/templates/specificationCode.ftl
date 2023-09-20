package ${groupId}.${lowerCaseTableName}.specification;

import javax.persistence.criteria.Predicate;

import ${groupId}.${lowerCaseTableName}.entity.${entityClassName}Entity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;



public class ${entityClassName}Specification {

    public static Specification<${entityClassName}Entity> create(${entityClassName}Entity filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            <#list metaDataDTOList as metaData>
             <#if metaData.sqlToJavaType() == "String">  
            if (StringUtils.hasText(filter.get${metaData.getFieldName()?cap_first}())) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("${metaData.getFieldName()}"), filter.get${metaData.getFieldName()?cap_first}()));
            }
            <#else>
            if (filter.get${metaData.getFieldName()?cap_first}() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("${metaData.getFieldName()}"), filter.get${metaData.getFieldName()?cap_first}()));
            }
            </#if>
            </#list>
            
            return predicate;
        };
    }

}
