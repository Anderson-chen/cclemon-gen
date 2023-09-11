package org.cclemongen.generator.output;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ANDYRepository {

    @PersistenceContext
    private EntityManager entityManager;

    
    
}
