package br.com.cingo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Log {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Conteúdo é obrigatório")
    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @NotNull(message = "Quantidade é obrigatório")
    @Column(nullable = false)
    private Long quantity;

    public void incrementQuantity() {
        setQuantity(getQuantity() + 1);
    }

}
