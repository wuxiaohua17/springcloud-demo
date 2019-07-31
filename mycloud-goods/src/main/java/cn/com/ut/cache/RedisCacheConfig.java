package cn.com.ut.cache;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/02/22 11:07
 */
@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisCacheProperties.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate,
			RedisCacheProperties redisCacheProperties) {

		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate, null,
				redisCacheProperties.isAllowNullValues());

		redisCacheManager.setUsePrefix(redisCacheProperties.isUsePrefix());
		redisCacheManager.setTransactionAware(redisCacheProperties.isTransactionAware());
		redisCacheManager.setDefaultExpiration(redisCacheProperties.getDefaultExpiration());

		if (redisCacheProperties.getExpires() != null
				&& !redisCacheProperties.getExpires().isEmpty()) {
			redisCacheManager.setExpires(redisCacheProperties.getExpires());
		}

		return redisCacheManager;
	}
}
