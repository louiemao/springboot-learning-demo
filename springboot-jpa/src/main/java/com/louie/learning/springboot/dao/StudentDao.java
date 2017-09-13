package com.louie.learning.springboot.dao;

import com.louie.learning.springboot.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface StudentDao extends Repository<Student, Integer> {

    @Query("select s from Student s where s.id=?1")
    Student loadById(int id);

    //根据地址和年龄进行查询
    List<Student> findByAddressAndAge(String address, int age);

    //根据id获取对象，即可返回对象，也可以返回列表
    Student readById(int id);

    //根据id获取列表，这里如果确定只有一个对象，也可以返回对象
    List<Student> getById(int id);

    //根据id获取一个对象，同样也可以返回列表
    Student findById(int id);
}
