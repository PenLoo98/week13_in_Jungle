package com.jungle.week13.Controller;

import com.jungle.week13.DTO.ForumDTO;
import com.jungle.week13.Entity.Forum;
import com.jungle.week13.Service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/forum")
public class ForumRestController {
    @Autowired
    private ForumService forumService;

    // GET
    @GetMapping("")
    public List<Forum> getForumsList() {
        return forumService.findAllForums();
    }

    @GetMapping("/{id}")
    public Forum getForum(@PathVariable Long id) {
        return forumService.findForum(id);
    }

    // POST
    @PostMapping("")
    public ResponseEntity<Forum> createForum(@RequestBody ForumDTO dto) {
        Forum created = forumService.create(dto);
        // 생성요청 결과에 따라 다른 응답값을 반환
        return (created == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :
                ResponseEntity.status(HttpStatus.OK).body(created);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Forum> updateForum(@PathVariable Long id, @RequestBody ForumDTO dto) {
        Forum updated = forumService.update(id, dto);
        return (updated == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :
                ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Forum> deleteForum(@PathVariable Long id){
        // 1. 서비스를 통해 게시글 삭제
        Forum deleted = forumService.delete(id);

        // 2. 삭제 결과에 따라 응답처리
        return deleted == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Transmission Test
    @PostMapping("/transaction-test")
    public ResponseEntity<List<Forum>> transactionTest
    (@RequestBody List<ForumDTO> dtos){
        // 1. 서비스에 dtos를 입력하여 보냄
        List<Forum> createdList = forumService.createForums(dtos);

        // 2. 돌아온 응답이 검증 후 적절한 반환하기
        return createdList == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build():
                ResponseEntity.status(HttpStatus.OK).body(createdList);

        // 3. 제대로된 응답일 경우 OK 응답 반환


    }
}
