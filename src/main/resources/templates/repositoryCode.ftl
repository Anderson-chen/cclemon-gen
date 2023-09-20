package ${groupId}.${lowerCaseTableName}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ${groupId}.${lowerCaseTableName}.entity.${entityClassName}Entity;

@Repository
public interface ${entityClassName}Repository extends JpaRepository<${entityClassName}Entity, Long>,JpaSpecificationExecutor<${entityClassName}Entity> {

}