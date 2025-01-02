package com.java.api.service;
import com.java.api.dto.StudentDto;
import com.java.api.entity.Student;
import com.java.api.exception.EmailAlreadyExistsException;
import com.java.api.exception.ResourceNotFoundException;
import com.java.api.mapper.StudentMapper;
import com.java.api.repository.StudentRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    public StudentDto createStudent(StudentDto studentDto) {
    	//converting StudentDto into Student JPA entity.
    	//Student student = StudentMapper.mapToStudent(studentDto);
    	
    	Optional<Student> optionalStudent = studentRepository.findByEmail(studentDto.getEmail());
    	if(optionalStudent.isPresent()) {
    		throw new EmailAlreadyExistsException("Student with the same email already exists!");
    	}
    	Student student = modelMapper.map(studentDto, Student.class);
    	
        Student savedStudent =  studentRepository.save(student);
        
        //converting Student JPA entity into studentDto
        //StudentDto savedStudentDto = StudentMapper.mapToStudentDto(savedStudent);
         
        StudentDto savedStudentDto = modelMapper.map(savedStudent, StudentDto.class);
        return savedStudentDto;
    }
    
    public StudentDto getStudentById(Long id) {
        Student student =  studentRepository.findById(id).orElseThrow(
        		  () -> new ResourceNotFoundException("Student", "id", id)
        );
        
        //return StudentMapper.mapToStudentDto(student);
        return modelMapper.map(student, StudentDto.class);
     }


    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        //return students.stream().map(StudentMapper::mapToStudentDto).collect(Collectors.toList());
        return students.stream().map((student) -> modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }
    
    public StudentDto updateStudent(StudentDto studentDto) {
    	Student existingStudent = studentRepository.findById(studentDto.getId()).orElseThrow(
    			() -> new ResourceNotFoundException("Student", "id", studentDto.getId())
    	);
    	existingStudent.setFirstName(studentDto.getFirstName());
    	existingStudent.setLastName(studentDto.getLastName());
    	existingStudent.setEmail(studentDto.getEmail());
    	
    	Student updatedStudent = studentRepository.save(existingStudent);
    	//return StudentMapper.mapToStudentDto(updatedStudent);
    	return modelMapper.map(updatedStudent, StudentDto.class);
    }

    public void deleteStudent(Long id) {
    	studentRepository.findById(id).orElseThrow(
    			() -> new ResourceNotFoundException("Student", "id", id)
    	);
        studentRepository.deleteById(id);
    }
}
