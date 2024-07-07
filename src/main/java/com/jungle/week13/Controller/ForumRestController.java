package com.jungle.week13.Controller;

import com.jungle.week13.DTO.ForumDTO;
import com.jungle.week13.Entity.Forum;
import com.jungle.week13.Service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ForumRestController {
    private final ForumService forumService;

    // GET
    @GetMapping("/forum")
    public ResponseEntity getForumsList() {
        List<Forum> findList =  forumService.findAllForums();
        return (findList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(findList) :
                ResponseEntity.status(HttpStatus.OK).body(findList);
    }

    @GetMapping("/forum/{id}")
    public Forum getForum(@PathVariable Long id) {
        return forumService.findForum(id);
    }

    // POST
    @PostMapping("/forum")
    public ResponseEntity<Forum> createForum(@RequestBody ForumDTO dto) {
        Forum created = forumService.create(dto);
        // 생성요청 결과에 따라 다른 응답값을 반환
        return (created == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :
                ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // PATCH
    @PatchMapping("/forum/{id}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody ForumDTO dto) {
        Forum updated = forumService.update(id, dto);
        return (updated == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :
                ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("/forum/{id}")
    public ResponseEntity<Forum> deleteForum(@PathVariable Long id){
        // 1. 서비스를 통해 게시글 삭제
        Forum deleted = forumService.delete(id);

        // 2. 삭제 결과에 따라 응답처리
        return deleted == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
