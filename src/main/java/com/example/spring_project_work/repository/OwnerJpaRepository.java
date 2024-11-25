package com.example.spring_project_work.repository;

import com.example.spring_project_work.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OwnerJpaRepository extends JpaRepository<Owner, Long> {

    @Modifying
    @Transactional
    @Query("update Owner o set o.firstName = :firstName, o.lastName = :lastName where o.id = :id")
    int updateOwner(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
