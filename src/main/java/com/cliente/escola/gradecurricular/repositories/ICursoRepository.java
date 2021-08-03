package com.cliente.escola.gradecurricular.repositories;

import com.cliente.escola.gradecurricular.entities.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {
    @Query("SELECT c FROM CursoEntity c WHERE LOWER(c.codigo) = :codigo")
    CursoEntity findByCodigo(@Param("codigo")  String codigo);
}
