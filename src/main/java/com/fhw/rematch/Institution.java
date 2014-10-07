package com.fhw.rematch;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class Institution
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)        
    private Long id;
  
    @Column private String name; 
    
    public Institution()
    {
        
    }
}
