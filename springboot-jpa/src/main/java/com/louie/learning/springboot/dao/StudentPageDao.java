package com.louie.learning.springboot.dao;

import com.louie.learning.springboot.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface StudentPageDao extends PagingAndSortingRepository<Student,Integer> {
    Page<Student> findByAge(int age, Pageable pageable);
    List<Student> findByAge(int age, Sort sort);
}
