package com.fhw.rematch

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import spock.lang.Specification
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import javax.persistence.Query
import spock.lang.Shared
import groovy.sql.Sql


class RuntimeParameterSpec 
    extends Specification 
{
    @Shared
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("rematch-TestPU")
    
    @Shared
    EntityManager em = emFactory.createEntityManager()    
    
    @Shared
    sql = Sql.newInstance('jdbc:derby:memory:test-jpa;create=false','app','app')
    
    
    
    def rtParams = [traunchSize:'50', numberOfTacos:'one hundred', numberOfBurritoes:'5']
    
    def "Verify basic run time parameter spec/test"()
    {
        given: 
            
            sql.withTransaction {
                sql.withBatch ('insert into RuntimeParameter(name,value) values (?,?)') { ps ->
                    rtParams.each{ k,v ->
                        ps.addBatch( [k,v] )                    
                    }
                }     
            }
           
            em.getTransaction().begin()
            def Query q = em.createQuery("Select r from RuntimeParameter r");
            def list = q.getResultList()
            em.getTransaction().commit()

        expect: 
            list.size() == 3
    }
    
    def cleanup() 
    {
        em.close()
        emFactory.close()
    }
    
}
