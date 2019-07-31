package cn.com.ut.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/01/28 09:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsUpdateDTO {

	private String goodsId;
	private String goodsName;
}
