package com.fhw.rematch

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import spock.lang.Specification
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import spock.lang.Shared
import groovy.sql.Sql


class CustomerSpec 
    extends Specification 
{
    @Shared
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("rematch-TestPU")
    
    @Shared
    EntityManager em = emFactory.createEntityManager()    
    
    @Shared
    sql = Sql.newInstance('jdbc:derby:memory:test-jpa;create=false','app','app')
    
    def "Verify basic customer with single institution add and persist"()
    {
        given: 
            def inst = new Institution()
            inst.setName('Test Bank')
            def cust = new Customer()
            cust.firstName = 'Joe'
            cust.lastName = 'Black'
            
            em.getTransaction().begin()
            em.persist(inst)
            em.flush()
            cust.addRelationshipInstitution(inst)
            em.persist(cust)
            em.getTransaction().commit()
            
            println "InstituionId = $inst.id"
            println "CusomterId = $cust.id"
        expect: 
            def rows = sql.rows('select * from Customer')
            def rows1 = sql.rows('select * from CustomerRelationshipInstitution')
            rows.size() == 1
        and: 
            rows[0].firstName == 'Joe'
        and: 
            rows[0].lastName == 'Black'
        and:             
            rows1.size() == 1
        and:
            rows1[0].customerId == cust.id
        and:
            rows1[0].instituionId == inst.id
    }
}
