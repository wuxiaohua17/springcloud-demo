package cn.com.ut.demo.dto;

import java.util.Set;

import lombok.Data;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Data
public class GoodsDTO {

	private String goodsId;
	private String goodsName;
	private Set<String> keywords;
	private OrderDTO orderDTO;
}
