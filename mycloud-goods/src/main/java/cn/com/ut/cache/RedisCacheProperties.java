package cn.com.ut.cache;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/02/22 11:31
 */
@ConfigurationProperties(prefix = "cache")
@Setter
@Getter
public class RedisCacheProperties {

	private long defaultExpiration = 0;
	private boolean transactionAware = false;
	private boolean usePrefix = true;
	private boolean allowNullValues = false;
	private Map<String, Long> expires;
}
