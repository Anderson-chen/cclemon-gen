package ${groupId}.${lowerCaseTableName}.service;

import java.util.List;

import  ${groupId}.${lowerCaseTableName}.dto.${entityClassName}DTO;
import  ${groupId}.${lowerCaseTableName}.entity.${entityClassName}Entity;
import  ${groupId}.${lowerCaseTableName}.repository.${entityClassName}Repository;
import  ${groupId}.${lowerCaseTableName}.specification.${entityClassName}Specification;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ${entityClassName}Service {

    private final ${entityClassName}Repository ${lowerCaseTableName}Repository;

    public List<${entityClassName}Entity> query(BodyDTO dto) {

        ${entityClassName}Entity entity = new ${entityClassName}Entity();

        BeanUtils.copyProperties(dto, entity);

        Specification<${entityClassName}Entity> spec = ${entityClassName}Specification.create(entity);

        return ${lowerCaseTableName}Repository.findAll(spec);

    }

}
