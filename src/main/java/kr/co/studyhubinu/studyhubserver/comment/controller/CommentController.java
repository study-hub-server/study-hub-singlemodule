package kr.co.studyhubinu.studyhubserver.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.studyhubinu.studyhubserver.comment.dto.request.CreateCommentRequest;
import kr.co.studyhubinu.studyhubserver.comment.dto.request.UpdateCommentRequest;
import kr.co.studyhubinu.studyhubserver.comment.service.CommentService;
import kr.co.studyhubinu.studyhubserver.user.dto.data.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "comment", description = "comment api")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping("")
    public ResponseEntity<HttpStatus> createComment(@Valid CreateCommentRequest request, UserId userId) {
        commentService.createComment(request, userId.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("")
    public ResponseEntity<HttpStatus> updateComment(@Valid UpdateCommentRequest request, UserId userId) {
        commentService.updateComment(request, userId.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId, UserId userId) {
        commentService.deleteComment(commentId, userId.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
