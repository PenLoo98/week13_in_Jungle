package com.jungle.week13.DTO;

import com.jungle.week13.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long id;
    private Long forum_id;
    private String nickname;
    private String body;

    public static CommentDTO entityToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getForum().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}