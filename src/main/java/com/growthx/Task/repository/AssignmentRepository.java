package com.growthx.Task.repository;

import com.growthx.Task.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findByAdminId(String adminId);
}
