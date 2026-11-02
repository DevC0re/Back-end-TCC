package com.devcore.medup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "enderecos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_enderecos_hospital", columnNames = "hospital_id")
})
@Getter
@Setter
@NoArgsConstructor
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 180)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(length = 150)
    private String complemento;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(length = 60)
    private String apelido;

    @Column(nullable = false)
    private boolean principal;

    // Um endereço pertence a um usuário OU a um hospital, não aos dois.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", unique = true)
    private HospitaisEntity hospital;
}
