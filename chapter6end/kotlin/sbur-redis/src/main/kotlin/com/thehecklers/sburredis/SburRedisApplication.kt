package com.thehecklers.sburredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@SpringBootApplication
class SburRedisApplication {
	@Bean
	fun redisOperations(factory: RedisConnectionFactory?): RedisOperations<String, Aircraft>? {
		val serializer = Jackson2JsonRedisSerializer(Aircraft::class.java)

		val template = RedisTemplate<String, Aircraft>()
		template.setConnectionFactory(factory!!)
		template.setDefaultSerializer(serializer)
		template.keySerializer = StringRedisSerializer()

		return template
	}
}

fun main(args: Array<String>) {
	runApplication<SburRedisApplication>(*args)
}
