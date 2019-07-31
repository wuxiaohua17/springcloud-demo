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
@Table(name = "shop_manage")
public class ShopManage {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "manager_id")
	private String managerId;
	@Column(name = "sub_store_id")
	private String subStoreId;
	@Column(name = "main_store_id")
	private String mainStoreId;
	@Column(name = "store_level")
	private Integer storeLevel;
}
