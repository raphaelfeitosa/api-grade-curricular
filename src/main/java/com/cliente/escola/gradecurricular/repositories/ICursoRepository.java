package com.cliente.escola.gradecurricular.repositories;

import com.cliente.escola.gradecurricular.entities.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {
    @Query("SELECT c FROM CursoEntity c WHERE c.codigo = :codigo")
    CursoEntity findCursoByCodigo(@Param("codigo") String codigo);
}
