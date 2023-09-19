package org.cclemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.cclemon.entity.${entityClassName};

@Repository
public interface ${entityClassName}Repository extends JpaRepository<${entityClassName}, Long> {

}