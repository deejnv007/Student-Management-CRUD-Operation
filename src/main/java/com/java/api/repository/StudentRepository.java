package com.java.api.repository;

import com.java.api.entity.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Additional query methods can be added here if needed
	Optional<Student> findByEmail(String email);
}
