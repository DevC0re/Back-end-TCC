package com.devcore.medup.repositories;

import com.devcore.medup.entities.ConvenioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConvenioRepository extends JpaRepository<ConvenioEntity, UUID>{
}
