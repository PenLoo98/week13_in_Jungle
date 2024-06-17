package com.jungle.week13.Repository;

import com.jungle.week13.Entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {

}