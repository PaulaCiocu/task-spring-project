package com.example.spring_project_work.service;

import com.example.spring_project_work.domain.EstimationAccuracy;
import com.example.spring_project_work.domain.Owner;
import com.example.spring_project_work.domain.Task;
import com.example.spring_project_work.dto.TaskDTO;
import com.example.spring_project_work.repository.OwnerJpaRepository;
import com.example.spring_project_work.repository.TaskJpaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskJpaRepository taskJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;

    public TaskService(TaskJpaRepository taskJpaRepository, OwnerJpaRepository ownerJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
        this.ownerJpaRepository = ownerJpaRepository;
    }

    public List<Task> listAllTasks() {
        return taskJpaRepository.findAll();
    }

    public ResponseEntity<Task> createTask(Task task) {
        //owner doesn't exist
        if (task.getOwner() != null) {
            // Check if the owner ID is valid
            if (task.getOwner().getId() > 0) {
                // Check if the owner exists in the Owner repository
                Optional<Owner> ownerOptional = ownerJpaRepository.findById(task.getOwner().getId());
                if (ownerOptional.isEmpty()) {
                    // Return 404 Not Found if the owner does not exist
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else {
                    task.setOwner(ownerOptional.get());
                }
            } else {
                // If the owner ID is null, return 400 Bad Request
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        // fields are null
        if(task.getCompletedHours() == null  || task.getRemainingHours() == null
            || task.getEstimatedDuration() == null || task.getTitle() == null || task.getDescription() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Task createdTask = taskJpaRepository.save(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED) ;
    }

    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> taskOptional = taskJpaRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            return new ResponseEntity<>(taskOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Task>> getAllTasksByOwnerId(@PathVariable Long ownerId) {
        Optional<Owner> ownerOptional = ownerJpaRepository.findById(ownerId);
        if (ownerOptional.isPresent()) {
            List<Task> tasksByOwnerId = taskJpaRepository.findTasksByOwnerId(ownerId);
            return new ResponseEntity<>(tasksByOwnerId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Task> deleteTaskById(Long taskId) {
        Optional<Task> taskOptional = taskJpaRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            taskJpaRepository.deleteById(taskId);
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> updateTaskRemainingHours(Long taskId, Integer remainingHours ) {
        Optional<Task> taskOptional = taskJpaRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            int updatedTask = taskJpaRepository.updateRemainingHours(taskId, remainingHours);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<EstimationAccuracy>  computeEstimationAccuracy(Long taskId) {
        Optional<Task> taskOptional = taskJpaRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            Integer estimatedHours = task.getEstimatedDuration();
            Integer completedHours = task.getCompletedHours();

            if(estimatedHours == 0 || estimatedHours == null){
                return ResponseEntity.ok(EstimationAccuracy.NOT_ESTIMATED);
            }
            if( 0.9 * estimatedHours <= completedHours && completedHours <= 1*1 * estimatedHours ){
                return ResponseEntity.ok(EstimationAccuracy.ACCURATELY_ESTIMATED);
            }
            if( completedHours < 0.9 * estimatedHours ){
                return ResponseEntity.ok(EstimationAccuracy.OVER_ESTIMATED);
            }
            if( completedHours > 1.1 * estimatedHours ){
                return ResponseEntity.ok(EstimationAccuracy.UNDER_ESTIMATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Task> getAllTasksByState(String state) {
        return taskJpaRepository.findByState(state);
    }
}
