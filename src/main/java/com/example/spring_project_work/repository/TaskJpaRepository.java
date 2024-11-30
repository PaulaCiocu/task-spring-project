package com.example.spring_project_work.repository;

import com.example.spring_project_work.domain.Task;
import com.example.spring_project_work.dto.TaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.status = :status")
    List<Task> findByState(@Param("status") String status);

    @Query("SELECT t FROM Task t WHERE t.owner.id = :ownerId")
    List<Task> findTasksByOwnerId(@Param("ownerId") Long ownerId);

    @Modifying
    @Transactional
    @Query("update Task t set t.remainingHours = :remainingHours where t.id = :id")
    int updateRemainingHours(@Param("id") Long id, @Param("remainingHours") Integer remainingHours);


}
