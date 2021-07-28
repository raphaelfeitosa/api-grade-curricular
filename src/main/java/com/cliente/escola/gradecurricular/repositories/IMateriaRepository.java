package com.cliente.escola.gradecurricular.repositories;

import com.cliente.escola.gradecurricular.entities.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {
}
