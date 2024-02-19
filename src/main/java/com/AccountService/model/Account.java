package com.AccountService.model;

//import jakarta.persistence.*;

import com.AccountService.constraint.DateParser;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Modelo de Cuenta.", description = "Representa una cuenta que puede tener un cliente.")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Id de Cuenta.", description = "Identificador único de una cuenta.", example = "1234")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20)
    @Schema(name = "Tipo de Cuenta.", description = "Define el tipo de cuenta del cliente.", example = "Ahorros, Gastos.")
    private String type;

    @NotNull
    @DateTimeFormat
    @Schema(name = "Fecha de Creación.", description = "La fecha en la que la cuenta fué abierta", example = "19/02/2024")
    Date openingDate;

    @NotNull
    @Min(0)
    @Schema(name = "Cantidad de Dinero.", description = "La cantidad de dinero que ha acumulado el cliente", example = "50593")
    private int balance;

    @NotNull
    @Schema(name = "Id del dueño", description = "Define al usuario que es dueño de la cuenta", example = "1234")
    private Long ownerId;

    @Transient
    @Schema(name = "Dueño de la Cuenta.", description = "Define al usuario que posee la cuenta", example = "{id: '', name: '', email: ''}")
    Customer owner;

}
