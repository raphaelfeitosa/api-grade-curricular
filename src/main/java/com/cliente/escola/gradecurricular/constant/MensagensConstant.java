package com.cliente.escola.gradecurricular.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum MensagensConstant {
    ERRO_GENERICO("Erro interno identificado. Contate o suporte."),
    ERRO_MATERIA_NAO_ENCONTRADA("Matéria não encontrada."),
    ERRO_MATERIA_CADASTRADA_ANTERIORMENTE("Matéria já possui cadastro."),
    ERRO_CURSO_CADASTRADO_ANTERIORMENTE("curso já possui cadastro."),
    ERRO_CURSO_NAO_ENCONTRADO("Curso não encontrado."),
    ERRO_MATERIA_NULL("Materia(as) não pode ser vazio ou nula"),
    ERRO_ID_INFORMADO("ID não pode ser informado na operação de cadastro."),
    ERRO_ID_NAO_INFORMADO("Informe o ID para alterar o cadastro");

    private final String valor;
}
