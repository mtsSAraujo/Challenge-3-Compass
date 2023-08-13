package com.compass.repository;

import com.compass.entity.PostRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostRequisition, Long> {

    Optional<PostRequisition> findByExternalId(Long externalId);

}
