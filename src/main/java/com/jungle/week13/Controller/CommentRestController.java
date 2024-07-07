package com.jungle.week13.Controller;

import com.jungle.week13.DTO.CommentDTO;
import com.jungle.week13.Service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/forum/")
@RequiredArgsConstructor
@CrossOrigin
public class CommentRestController {
    private final CommentService commentService;
    // 1. 댓글 조회
    @GetMapping("/{forum_id}/comments")
    public ResponseEntity<List<CommentDTO>> commentsGET(@PathVariable Long forum_id){
        // 서비스에 forum_id 맞는 comments 요청
        List<CommentDTO> dtos = commentService.getComments(forum_id);
        // 결과 응답
        if(dtos == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/{forum_id}/comments")
    public ResponseEntity<CommentDTO> commentPOST(@PathVariable Long forum_id,
                                                  @RequestBody CommentDTO dto){
        // 서비스에서 CREATE 처리
        CommentDTO createdDTO = commentService.postComment(forum_id,dto);

        // 결과 응답
        if(createdDTO==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(createdDTO);

    }

    // 3. 댓글 수정
    @PatchMapping("/{forum_id}/comments/{commentId}")
    public ResponseEntity<CommentDTO> commentPATCH(@PathVariable Long forum_id,
                                                   @PathVariable Long commentId,
                                                   @RequestBody CommentDTO dto){
        // 서비스에서 로직처리
        CommentDTO updatedDTO = commentService.patchComment(forum_id, commentId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDTO);

    }

    // 4. 댓글 삭제
    @DeleteMapping("{forum_id}/comments/{commentId}")
    public ResponseEntity<CommentDTO> commentDELETE(@PathVariable Long forum_id,
                                                    @PathVariable Long commentId){
        // 서비스에서 로직처리
        CommentDTO deletedDTO = commentService.deleteComment(forum_id, commentId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDTO);
    }
}
