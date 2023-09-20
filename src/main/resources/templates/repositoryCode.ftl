package ${groupId}.${lowerCaseTableName}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ${groupId}.entity.${lowerCaseTableName}Entity;

@Repository
public interface ${entityClassName}Repository extends JpaRepository<${entityClassName}Entity, Long> {

}