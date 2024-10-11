package com.growthx.Task.services.Impl;

import com.growthx.Task.model.Assignment;
import com.growthx.Task.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment submitAssignment(Assignment assignment) {
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setStatus("PENDING");
        return assignmentRepository.save(assignment);
    }
    public List<Assignment> getAssignmentsForAdmin(String adminId) {
        return assignmentRepository.findByAdminId(adminId);
    }

    public Assignment acceptAssignment(String assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        assignment.setStatus("ACCEPTED");
        return assignmentRepository.save(assignment);
    }

    public Assignment rejectAssignment(String assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        assignment.setStatus("REJECTED");
        return assignmentRepository.save(assignment);
    }
}
