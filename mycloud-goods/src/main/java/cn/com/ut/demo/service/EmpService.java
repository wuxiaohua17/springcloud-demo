package cn.com.ut.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.ut.demo.entity.Emp;
import cn.com.ut.demo.repository.EmpRepository;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/07/04 12:08
 */
@Service
public class EmpService {

	@Autowired
	private EmpRepository empRepository;

	@org.springframework.transaction.annotation.Transactional
	public void add() {

		Emp emp = new Emp();
		emp.setEmpName("wu");
		emp = empRepository.save(emp);
		// emp = empRepository.getOne(emp.getId());
		emp.setEmpName("liu");
	}
}
