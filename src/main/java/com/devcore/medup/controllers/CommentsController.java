package com.devcore.medup.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcore.medup.dtos.CommentsRpsDto;
import com.devcore.medup.dtos.CommentsRqsDto;
import com.devcore.medup.services.CommentsService;



import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity <CommentsRpsDto> saveComments (@RequestBody @Valid CommentsRqsDto request) {

        CommentsRpsDto response =commentsService.SaveComments(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

    @GetMapping("/{id}")
    public ResponseEntity <CommentsRpsDto> getBy(@PathVariable UUID id){
        CommentsRpsDto response = commentsService.ShearchById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity <List<CommentsRpsDto>> getAll() {
        List<CommentsRpsDto> list = commentsService.listAll();

        return ResponseEntity.ok(list);
    }
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        commentsService.delete(id);
        return ResponseEntity.noContent().build(); //Retorna HTTP 204
    }
    
}
