package br.com.gerenciamentomudancasapi.model;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import jakarta.validation.groups.Default;

@Entity
@Table(name = "\"Sistema\"", schema = "public")
public class Sistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "\"createdAt\"")
    private OffsetDateTime createdAt;

    @Column(name = "\"updatedAt\"")
    private OffsetDateTime updatedAt;

    protected Sistema(){}
}
