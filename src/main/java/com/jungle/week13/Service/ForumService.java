package com.jungle.week13.Service;

import com.jungle.week13.DTO.ForumDTO;
import com.jungle.week13.Entity.Forum;
import com.jungle.week13.Repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;

    public List<Forum> findAllForums() {
        return forumRepository.findAll();
    }

    public Forum findForum(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

    public Forum create(ForumDTO dto) {
        // dto -> Entity
        Forum newForum = dto.toEntity();

        // json의 데이터에 id값이 입력되었는지 확인
        if(newForum.getId() != null){
            return null;
        }

        // Entity -> DB 저장
        return forumRepository.save(newForum);
    }

    public Forum update(Long id, ForumDTO dto) {
        // 1. dto -> Entity
        Forum edited = dto.toEntity();
        log.info(edited.toString());

        // 2. target 조회
        Forum target = forumRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리
        if(id != edited.getId() || target == null){
            log.info("잘못된 요청!! id: {}, edited: {}", id, edited.toString());
            return null;
        }

        // 4. Updated 하기
        target.patch(edited);
        Forum updated = forumRepository.save(target);
        return updated;
    }

    public Forum delete(Long id) {
        // 1. 대상 forum 찾기
        Forum target = forumRepository.findById(id).orElse(null);

        // 2. 없는 forum인 경우 - null 반환
        if (target == null){
            return null;
        }

        // 3. 있는 forum인 경우 - 삭제한 forum 반환
        forumRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Forum> createForums(List<ForumDTO> dtos) {
        // 1. dtos -> Entities
        List<Forum> forumList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 2. Entities -> save to DB
        forumList.stream()
                .forEach(forum -> forumRepository.save(forum));

        // 3. 강제 예외 발생시키기
        forumRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("데이터 찾기 실패!"));

        // 4. 결과 값 반환하기
        return forumList;
    }
}
