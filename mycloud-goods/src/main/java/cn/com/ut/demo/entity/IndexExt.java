package cn.com.ut.demo.entity;

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
@Table(name = "t_index_ext")
public class IndexExt {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "perf_id")
	private String perfId;
	@Column(name = "ext_name")
	private String extName;
	@Column(name = "ext_amount")
	private Integer extAmount;
}
