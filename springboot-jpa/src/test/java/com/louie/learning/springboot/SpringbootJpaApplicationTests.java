package com.louie.learning.springboot;

import com.louie.learning.springboot.dao.StudentCrudDao;
import com.louie.learning.springboot.dao.StudentDao;
import com.louie.learning.springboot.dao.StudentPageDao;
import com.louie.learning.springboot.dao.StudentSpecificationDao;
import com.louie.learning.springboot.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaApplicationTests {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentCrudDao studentCrudDao;
    @Autowired
    private StudentPageDao studentPageDao;
    @Autowired
    private StudentSpecificationDao studentSpecificationDao;

    @Test
    public void testStudent() {
        Assert.assertEquals("foo", studentDao.findById(1).getName());
        Assert.assertEquals("foo", studentDao.readById(1).getName());
        Assert.assertEquals(1, studentDao.getById(1).size());
        Assert.assertEquals("foo", studentDao.loadById(1).getName());
        Assert.assertEquals(2, studentDao.findByAddressAndAge("zt", 22).size());
    }

    @Test
    public void testAddStudent() {
        //添加操作
        Student stu = new Student("foo", "km", 22);
        studentCrudDao.save(stu);
    }

    @Test
    public void testUpdateStudent() {
        /*修改的操作*/
        Student stu = studentCrudDao.findOne(1);
        stu.setName("bar1");
        studentCrudDao.save(stu);
    }

    @Test
    public void testDelete() {
        //删除操作
        studentCrudDao.delete(1);
    }

    @Test
    public void testCount() {
        //取数量操作
        Assert.assertEquals(3, studentCrudDao.count());
        Assert.assertEquals(2, studentCrudDao.countByAge(22));
    }

    @Test
    public void testPage() {
        //显示第1页每页显示3条
        PageRequest pr = new PageRequest(1, 3);
        //根据年龄进行查询
        Page<Student> stus = studentPageDao.findByAge(22, pr);
        Assert.assertEquals(2, stus.getTotalPages());
        Assert.assertEquals(6, stus.getTotalElements());
        Assert.assertEquals(1, stus.getNumber());
    }

    @Test
    public void testSort() {
        //设置排序方式为name降序
        List<Student> stus = studentPageDao.findByAge(22
                , new Sort(Sort.Direction.DESC, "name"));
        Assert.assertEquals(5, stus.get(0).getId());

        //设置排序以name和address进行升序
        stus = studentPageDao.findByAge(22
                , new Sort(Sort.Direction.ASC, "name", "address"));
        Assert.assertEquals(8, stus.get(0).getId());

        //设置排序方式以name升序，以address降序
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.ASC, "name"),
                new Sort.Order(Sort.Direction.DESC, "address"));

        stus = studentPageDao.findByAge(22, sort);
        Assert.assertEquals(7, stus.get(0).getId());
    }

    @Test
    public void testSpecificaiton() {
        List<Student> stus = studentSpecificationDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //root.get("address")表示获取address这个字段名称,like表示执行like查询,%zt%表示值
                Predicate p1 = criteriaBuilder.like(root.get("address"), "%zt%");
                Predicate p2 = criteriaBuilder.greaterThan(root.get("id"), 3);
                //将两个查询条件联合起来之后返回Predicate对象
                return criteriaBuilder.and(p1, p2);
            }
        });
        Assert.assertEquals(2, stus.size());
        Assert.assertEquals("oo", stus.get(0).getName());
    }

    @Test
    public void testSpecificaiton2() {
        //第一个Specification定义了两个or的组合
        Specification<Student> s1 = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("id"), "2");
                Predicate p2 = criteriaBuilder.equal(root.get("id"), "3");
                return criteriaBuilder.or(p1, p2);
            }
        };
        //第二个Specification定义了两个or的组合
        Specification<Student> s2 = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.like(root.get("address"), "zt%");
                Predicate p2 = criteriaBuilder.like(root.get("name"), "foo%");
                return criteriaBuilder.or(p1, p2);
            }
        };
        //通过Specifications将两个Specification连接起来，第一个条件加where，第二个是and
        List<Student> stus = studentSpecificationDao.findAll(Specifications.where(s1).and(s2));

        Assert.assertEquals(1, stus.size());
        Assert.assertEquals(3, stus.get(0).getId());
    }
}
