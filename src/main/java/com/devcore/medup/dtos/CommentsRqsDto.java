package com.devcore.medup.dtos;

import com.devcore.medup.enums.AssessmentEnum;
import com.devcore.medup.enums.StatusEnum;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data  
@NoArgsConstructor
@AllArgsConstructor
public class CommentsRqsDto {

    @Size(min=0, max=1000, message = "Comments must be between 0 and 1000 characters")
    private String comments;
    
    public StatusEnum status;
    
    private AssessmentEnum assessment;
    
    private LocalDate date;
}
