package cn.com.ut.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.demo.entity.Emp;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/04/22 16:12
 */
public interface EmpRepository extends JpaRepository<Emp, Long> {

}
