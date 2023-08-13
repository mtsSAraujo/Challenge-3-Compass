package com.compass.repository;


import com.compass.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findByPostRequisitionExternalId(Long externalId);

}
