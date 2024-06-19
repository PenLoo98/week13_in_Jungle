package com.jungle.week13.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Entity(name = "forum")
@NoArgsConstructor
@Data
public class Forum {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "forum", cascade=CascadeType.REMOVE)
    private List<Comment> comments;

    // 변경사항 업데이트
    public void patch(Forum forum) {
        if(forum.title != null){
            this.title = forum.title;
        }
        if(forum.content != null){
            this.content = forum.content;
        }
    }
}