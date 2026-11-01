package com.devcore.medup.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcore.medup.dtos.CommentsRpsDto;
import com.devcore.medup.dtos.CommentsRqsDto;
import com.devcore.medup.entities.Comments;
import com.devcore.medup.repositories.CommentsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository repository;

    public CommentsRpsDto saveComments(CommentsRqsDto request) {
        Comments comments = new Comments();
        comments.setComments(request.getComments());
        comments.setStatus(request.getStatus());
        comments.setAssessment(request.getAssessment());
        comments.setDate(LocalDate.now());

        Comments save = repository.save(comments);
        return convertByRpsDto(save);
    }

    public CommentsRpsDto searchById(UUID id) {
        Comments comments = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado com ID: " + id));

        return convertByRpsDto(comments);
    }

    public List<CommentsRpsDto> listAll() {
        return repository.findAll()
                .stream()
                .map(this::convertByRpsDto)
                .toList();
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Comentário não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private CommentsRpsDto convertByRpsDto(Comments comments) {
        return new CommentsRpsDto(
                comments.getCommentsId(),
                comments.getComments(),
                comments.getStatus(),
                comments.getAssessment(),
                comments.getDate()
        );
    }
}