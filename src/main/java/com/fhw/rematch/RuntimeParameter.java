package com.fhw.rematch;
import java.io.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class RuntimeParameter
    implements Serializable
{
    @Id
    private String name; 

    @Column
    private String value;
    
    public RuntimeParameter()
    {
        
    }

}
