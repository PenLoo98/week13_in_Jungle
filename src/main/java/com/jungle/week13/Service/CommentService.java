package com.jungle.week13.Service;

import com.jungle.week13.DTO.CommentDTO;
import com.jungle.week13.Entity.Comment;
import com.jungle.week13.Entity.Forum;
import com.jungle.week13.Repository.CommentRepository;
import com.jungle.week13.Repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ForumRepository forumRepository;

    public List<CommentDTO> getComments(Long forumId) {
        // stream 사용한 방식
        return commentRepository.findByForumId(forumId)
                .stream()
                .map(comment -> CommentDTO.entityToDTO(comment))
                .collect(Collectors.toList()); // 자료형 변환: Stream -> List
    }

    @Transactional
    public CommentDTO postComment(Long forumId, CommentDTO dto) {
        // 1. 게시글 조회 및 예외 발생
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 2. 댓글 entity 생성
        Comment comment = Comment.dtoToEntity(dto, forum);

        // 3. 댓글 entity를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO로 변환해 반환
        return CommentDTO.entityToDTO(created);
    }

    @Transactional
    public CommentDTO patchComment(Long commentId, CommentDTO dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(commentId)
                .orElseThrow( () -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다.") );

        // 2. 댓글 수정
        target.patch(dto);

        // 3. DB에 갱신
        Comment updated = commentRepository.save(target);

        // 4. 결과 반환
        return CommentDTO.entityToDTO(updated);
    }

    @Transactional
    public CommentDTO deleteComment(Long commentId){
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(commentId)
                .orElseThrow( () -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        // 2. 댓글 삭제
        commentRepository.delete(target);

        // 3. 결과 반환 (Entity -> DTO)
        return CommentDTO.entityToDTO(target);
    }
}
