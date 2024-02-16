package com.AccountService.model;

//import jakarta.persistence.*;

import com.AccountService.constraint.DateParser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20)
    private String type;

    @NotNull
    //@DateParser
    @DateTimeFormat
    Date openingDate;

    @NotNull
    @Min(0)
    private int balance;

    @NotNull
    private Long ownerId;

    @Transient
    Customer owner;


}
