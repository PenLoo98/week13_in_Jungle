package com.jungle.week13.Repository;

import com.jungle.week13.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value="SELECT * FROM forum_comment WHERE forum_id = :forumId", nativeQuery = true)
    List<Comment> findByForumId(Long forumId);

    // 특정 게시글의 특정 댓글 조회
    @Query(value="SELECT * FROM forum_comment WHERE forum_id = :forumId AND id = :commentId", nativeQuery = true)
    Comment findByForumIdAndCommentId(Long forumId, Long commentId);

    // 특정 닉네임의 모든 댓글 조회
//    @Query(value="SELECT * FROM comment WHERE nickname = :nickname", nativeQuery = true)
    List<Comment> findByNickname(String nickname);
}
