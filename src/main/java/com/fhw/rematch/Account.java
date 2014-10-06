package com.fhw.rematch;

import java.io.*;
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
    
    private Customer customer;
    private Institution institution;
    
    public Account()
    {
        
    }
}
