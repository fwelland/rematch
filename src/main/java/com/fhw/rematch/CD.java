package com.fhw.rematch;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class CD 
    implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long amount;

    @ManyToOne
    private Account owningAccount;

    public CD()
    {

    }

}
