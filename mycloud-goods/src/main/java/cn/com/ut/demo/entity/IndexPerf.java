package cn.com.ut.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/04/22 16:05
 */
@Data
@Entity
@Table(name = "t_index_perf")
public class IndexPerf {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "second_name")
	private String secondName;
	@Column(name = "update_time")
	private Date updateTime;
	@Column(name = "total")
	private Long total;
	@Column(name = "times")
	private Long times;
}
