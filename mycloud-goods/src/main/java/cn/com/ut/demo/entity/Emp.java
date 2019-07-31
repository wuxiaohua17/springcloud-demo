package cn.com.ut.demo.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/04/22 16:05
 */
@Data
@Entity
@Table(name = "emp")
public class Emp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "emp_name")
	private String empName;
}
