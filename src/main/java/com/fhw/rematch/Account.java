package com.fhw.rematch;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class Account
    implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Long id;
    
    @Column private Long amount;
    @Column private String name; 

    @OneToMany
    @JoinTable
    (
        name="AccountOwner",
        joinColumns={ @JoinColumn(name="AccountId", referencedColumnName="ID") },
        inverseJoinColumns={ @JoinColumn(name="CustomerId", referencedColumnName="ID", unique=true) }
    )
    private List<Customer> owners;
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="institutionId")    
    private Institution institution;
    
    @Column private int interestRate; 
    
    @OneToMany(mappedBy = "owningAccount")
    private List<CD> cds; 
    
    public Account()
    {
        
    }
    
    public void addOwner(Customer c)
    {
        if(null == owners)
        {
            owners = new ArrayList<>();
        }
        owners.add(c);
    }    
    
    public void addCD(CD cd)
    {
        if(null == cds)
        {
            cds = new ArrayList<>();
        }
        cds.add(cd);        
    }
}
