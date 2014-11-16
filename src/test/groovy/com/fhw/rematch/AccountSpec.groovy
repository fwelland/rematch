package com.fhw.rematch

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import spock.lang.Specification
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import spock.lang.Shared
import groovy.sql.Sql


class AccountSpec 
    extends Specification 
{
    @Shared
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("rematch-TestPU")
    
    @Shared
    EntityManager em = emFactory.createEntityManager()    
    
    @Shared
    sql = Sql.newInstance('jdbc:derby:memory:test-jpa;create=false','app','app')
    
    def "Verify basic account for single customer at single institution add and persist"()
    {
        given: 
            def inst = new Institution()
            inst.setName('Account Test Bank')
            def cust = new Customer()
            cust.firstName = 'Account Joe'
            cust.lastName = 'Black'
            def acct = new Account()
            acct.name = 'My first account'
            acct.amount = 1000
            
            em.getTransaction().begin()
            em.persist(inst)
            em.flush()
            cust.addRelationshipInstitution(inst)
            em.persist(cust)
            em.flush()
            acct.addOwner(cust)
            acct.institution = inst
            em.persist(acct)
            
            def CD cd = new CD()
            cd.setAmount(100)
            cd.setOwningAccount(acct)
            em.persist(cd)
            
            em.getTransaction().commit()
            
            println "InstituionId = $inst.id"
            println "CusomterId = $cust.id"
            println "AccountId = $acct.id"
            println "CdId = $cd.id"
        expect: 
            def rows = sql.rows('select * from Customer')
            def rows1 = sql.rows('select * from CustomerRelationshipInstitution')
            def rows2 = sql.rows('select * from Account')
            def rows3 = sql.rows('select * from AccountOwner')
            def rows4 = sql.rows('select * from CD')
            println rows4[0]
            rows.size() == 1
        and: 
            rows[0].firstName == 'Account Joe'
        and: 
            rows[0].lastName == 'Black'
        and:             
            rows1.size() == 1
        and:
            rows1[0].customerId == cust.id
        and:
            rows1[0].instituionId == inst.id
        and: 
            rows2.size() == 1
        and:
            rows2[0].name == 'My first account'
        and:
            rows2[0].institutionId == inst.id            
        and:
            rows3.size() == 1
        and: 
            rows3[0].customerId == cust.id
        and:
            rows3[0].accountId == acct.id
        and:
            rows4.size() == 1
        and:
            rows4[0].amount == 100
        and:
            rows4[0].owningAccount_ID == acct.id
    }
    
    def cleanup() 
    {
        em.close()
        emFactory.close()
    }
    
}
