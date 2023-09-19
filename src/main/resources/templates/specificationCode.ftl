package org.cclemon.specification;


public class ${entityClassName}Specification {

    public static Specification<${entityClassName}> create(${entityClassName} filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            
            
            <#list metaDataDTOList as metaData>
            if (StringUtils.isNotBlank(filter.get${metaData.getFieldName()?cap_first}())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("${metaData.getFieldName()}"), filter.get${metaData.getFieldName()?cap_first}()));
            }
            </#list>
            
            return predicate;
        };
    }

}
