package com.louie.learning.springboot.dao;

import com.louie.learning.springboot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/9/12.
 */
public interface StudentSpecificationDao extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
}
