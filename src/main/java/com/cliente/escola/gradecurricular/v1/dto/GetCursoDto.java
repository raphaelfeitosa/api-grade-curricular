package com.cliente.escola.gradecurricular.v1.dto;

import com.cliente.escola.gradecurricular.entities.CursoEntity;
import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetCursoDto extends RepresentationModel<GetCursoDto> {

    private Long id;

    @NotBlank(message = "Informe o nome do curso")
    @Size(min = 10, max = 30)
    private String nome;

    @NotBlank(message = "Informe o codigo do curso")
    @Size(min = 2, max = 5)
    private String codigo;

    List<MateriaEntity> materias;

    public GetCursoDto(CursoEntity cursoEntity) {
        id = cursoEntity.getId();
        nome = cursoEntity.getNome();
        codigo = cursoEntity.getCodigo();
        materias = cursoEntity.getMaterias();
    }
}
