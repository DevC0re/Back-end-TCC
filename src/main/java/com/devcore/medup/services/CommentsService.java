package com.devcore.medup.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcore.medup.dtos.CommentsRpsDto;
import com.devcore.medup.dtos.CommentsRqsDto;
import com.devcore.medup.entities.Comments;
import com.devcore.medup.repositories.CommentsRepository;

import java.util.List;
import java.util.UUID;


import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {
    
    private final CommentsRepository repository;

    //criar commentario
    public CommentsRpsDto SaveComments(CommentsRqsDto request){
    
        Comments comments = new Comments();
        comments.setComments(request.getComments());
        comments.setStatus(request.getStatus());
        comments.setAssessment(request.getAssessment());
        comments.setDate(LocalDate.now());

        Comments save = repository.save(comments);
        return convertByRpsDto(save);

    }
    //buscar por id
    public CommentsRpsDto ShearchById(UUID Id){
        Comments comments = repository.findById(Id)
        .orElseThrow(() -> new RuntimeException
        ("Comentário não encontrado com ID" + Id));

        return convertByRpsDto(comments);
    }
    // listar comentario
    public List<CommentsRpsDto> listAll(){
        return repository.findAll()
        .stream()
        .map(this::convertByRpsDto)
        .toList();
    }
    // deletar por id
    public void delete (UUID id){

        if (!repository.existsById(id)){
            throw new RuntimeException("Comentário não encontrado com ID:" + id);
                    }
           
        repository.deleteById(id);  
    }

    //Método utilitario privado para converter entidfae -> responsedtoa
    private CommentsRpsDto convertByRpsDto(Comments comments){
        return new CommentsRpsDto(
            comments.getCommentsId(),
            comments.getComments(),
            comments.getStatus(),
            comments.getAssessment(),
            comments.getDate()
        );
    }
}
