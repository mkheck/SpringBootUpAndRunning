package com.thehecklers.sburredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class SburRedisApplication {
	@Bean
	public RedisOperations<String, Aircraft>
	redisOperations(RedisConnectionFactory factory) {
		JacksonJsonRedisSerializer<Aircraft> serializer =
				new JacksonJsonRedisSerializer<>(Aircraft.class);

		RedisTemplate<String, Aircraft> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setDefaultSerializer(serializer);
		template.setKeySerializer(new StringRedisSerializer());

		return template;
	}

	static void main(String[] args) {
		SpringApplication.run(SburRedisApplication.class, args);
	}

}
