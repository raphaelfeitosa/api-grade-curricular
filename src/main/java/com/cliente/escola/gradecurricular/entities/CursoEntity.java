package com.cliente.escola.gradecurricular.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_cursos")
@Data
@NoArgsConstructor
public class CursoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "nome")
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "codigo")
    private String codigo;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "tb_curso_materia",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id"))
    private List<MateriaEntity> materias = new ArrayList<>();
}
