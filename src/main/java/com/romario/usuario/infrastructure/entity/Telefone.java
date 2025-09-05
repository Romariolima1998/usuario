package com.romario.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero", length = 11)
    private String numero;

    @Column(name = "ddd", length = 3)
    private String ddd;
}
