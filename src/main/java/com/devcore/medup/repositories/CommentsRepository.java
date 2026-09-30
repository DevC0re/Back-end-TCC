package com.devcore.medup.repositories;

import com.devcore.medup.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, UUID> {


    
}
