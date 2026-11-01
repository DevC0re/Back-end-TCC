package com.devcore.medup.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devcore.medup.dtos.CommentsRpsDto;
import com.devcore.medup.dtos.CommentsRqsDto;
import com.devcore.medup.services.CommentsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<CommentsRpsDto> saveComments(@RequestBody @Valid CommentsRqsDto request) {
        CommentsRpsDto response = commentsService.saveComments(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentsRpsDto> getBy(@PathVariable UUID id) {
        CommentsRpsDto response = commentsService.searchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentsRpsDto>> getAll() {
        List<CommentsRpsDto> list = commentsService.listAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commentsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}