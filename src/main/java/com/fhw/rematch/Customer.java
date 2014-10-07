package com.fhw.rematch;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
class Customer
    implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)            
    private Long id; 
    
    @Column
    private String firstName;
    
    @Column
    private String lastName; 
    
    @OneToMany
    @JoinTable
    (
        name="CustomerRelationshipInstitution",
        joinColumns={ @JoinColumn(name="CustomerId", referencedColumnName="ID") },
        inverseJoinColumns={ @JoinColumn(name="InstituionId", referencedColumnName="ID", unique=true) }
    )
    private List<Institution> relationshipInstituions; 
    
    public Customer()
    {
        
    }
    
    public void addRelationshipInstitution(Institution i)
    {
        if(null == relationshipInstituions)
        {
            relationshipInstituions = new ArrayList<>();
        }
        relationshipInstituions.add(i);
    }
   
}
