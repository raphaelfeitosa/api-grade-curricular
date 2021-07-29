package com.cliente.escola.gradecurricular.repositories;

import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {
    @Query("SELECT m FROM MateriaEntity m WHERE m.horas >= :horaMinima")
    public List<MateriaEntity> findByHorarioMinimo(@Param("horaMinima") int horaMinima);

    @Query("SELECT m FROM MateriaEntity m WHERE m.frequencia = :frequencia")
    public List<MateriaEntity> findByFrequencia(@Param("frequencia") int frequencia);
}
