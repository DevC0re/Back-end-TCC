package com.devcore.medup.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.devcore.medup.enums.StatusEnum;
import com.devcore.medup.enums.AssessmentEnum;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class CommentsRpsDto {
    
    private UUID commentsId;
    @Size(max = 1000, message = "Comment must be less than 500 characters")
    private String comment;
    private StatusEnum status;
    private AssessmentEnum assessment;
    private LocalDate date;
}
