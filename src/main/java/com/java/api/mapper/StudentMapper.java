package com.java.api.mapper;

import com.java.api.dto.StudentDto;
import com.java.api.entity.Student;

public class StudentMapper {
	
    // Convert Student JPA Entity into StudentDto
	public static StudentDto mapToStudentDto(Student student) {
		StudentDto studentDto = new StudentDto(
				student.getId(),
				student.getFirstName(),
				student.getLastName(),
				student.getEmail()
				);
		return studentDto;
	}
	
	// Convert StudentDto into Student JPA Entity
	public static Student mapToStudent(StudentDto studentDto) {
		Student student = new Student(
				studentDto.getId(),
				studentDto.getFirstName(),
				studentDto.getLastName(),
				studentDto.getEmail()
				);
		return student;
	}
}
