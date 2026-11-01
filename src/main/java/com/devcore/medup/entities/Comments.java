package com.devcore.medup.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import java.time.LocalDate;
import com.devcore.medup.enums.AssessmentEnum;
import com.devcore.medup.enums.StatusEnum;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comments{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentsId;

    private String comments;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
   
    @Enumerated(EnumType.STRING)
    private AssessmentEnum assessment;
   
    private LocalDate date;

    //@manyToOne
    //private Users usersId;

    //@manyToOne
    //private Hospitals hospitalsId;
}