package com.fhw.rematch

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import spock.lang.Specification
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import spock.lang.Shared
import groovy.sql.Sql


class InstitutionSpec 
    extends Specification 
{
    @Shared
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("rematch-TestPU")
    
    @Shared
    EntityManager em = emFactory.createEntityManager()    
    
    @Shared
    sql = Sql.newInstance('jdbc:derby:memory:test-jpa;create=false','app','app')
    
    def "Verify basic institution add and persist"()
    {
        given: 
            def inst = new Institution()
            inst.setName('Test Bank')
            em.getTransaction().begin()
            em.persist(inst)
            em.getTransaction().commit()
            
        expect: 
            def rows = sql.rows('select * from Institution')
            rows.size() == 1
        and: 
            rows[0].name == 'Test Bank'
    }
}
