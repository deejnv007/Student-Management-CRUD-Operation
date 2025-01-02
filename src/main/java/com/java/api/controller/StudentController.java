package com.java.api.controller;

import com.java.api.dto.StudentDto;
import com.java.api.entity.Student;
import com.java.api.exception.ErrorDetails;
//import com.java.api.exception.ErrorDetails;
import com.java.api.exception.ResourceNotFoundException;
import com.java.api.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto savedStudentDto = studentService.createStudent(studentDto);
        return new ResponseEntity<>(savedStudentDto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        StudentDto getStudentDto =  studentService.getStudentById(id);
        return new ResponseEntity<>(getStudentDto,HttpStatus.OK);
    }
 
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
    	List<StudentDto> studentsDtos = studentService.getAllStudents();
    	 return new ResponseEntity<>(studentsDtos,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDto studentDto){
    	studentDto.setId(id);
    	StudentDto updatedStudent = studentService.updateStudent(studentDto);
    	return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }
    
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
//    	ErrorDetails errorDetails = new ErrorDetails(
//    		LocalDateTime.now(),
//    		exception.getMessage(),
//    		webRequest.getDescription(false),
//    		"USER_NOT_FOUND!"
//    	);
//    	return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);    }
}
