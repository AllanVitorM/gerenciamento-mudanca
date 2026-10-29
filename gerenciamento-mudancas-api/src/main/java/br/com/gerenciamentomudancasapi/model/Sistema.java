package br.com.gerenciamentomudancasapi.model;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import jakarta.validation.groups.Default;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

    @CreationTimestamp
    @Column(name = "\"createdAt\"", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "\"updatedAt\"", nullable = false)
    private OffsetDateTime updatedAt;

    protected Sistema(){}

    public Sistema(String nome) {
        this.nome = nome;
        this.ativo = true;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void alterarNome(String novoNome){
        this.nome = novoNome;
    }

    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo=false;
    }
}
