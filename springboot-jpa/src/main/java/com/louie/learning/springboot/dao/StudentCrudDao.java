package com.louie.learning.springboot.dao;


import com.louie.learning.springboot.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentCrudDao extends CrudRepository<Student, Integer> {
    //增加了一个countByXX的方法
    long countByAge(int age);
}