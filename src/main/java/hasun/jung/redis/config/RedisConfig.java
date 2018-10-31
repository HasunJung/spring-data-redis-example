package hasun.jung.redis.config;

import hasun.jung.redis.service.RedisMessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	RedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
		return new JedisConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new RedisMessageSubscriber());
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(jedisConnectionFactory());
		listenerContainer.addMessageListener(messageListener(), topic());
		return listenerContainer;
	}

	@Bean
	PatternTopic topic() {
		return new PatternTopic("__keyevent@*__:expired");
	}

//	@Bean
//	RedisMessageListenerContainer keyExpirationListenerContainer() {
//		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
//		listenerContainer.setConnectionFactory(jedisConnectionFactory());
//		listenerContainer.addMessageListener((message, pattern) -> {
//			log.debug("!!!!!!!!!keyExpirationListenerContainer : {}", pattern);
//			// event handling comes here
//		}, new PatternTopic("__keyevent@*__:expired"));
//		return listenerContainer;
//	}


//	@Bean
//	public RedisCustomConversions redisCustomConversions(OffsetDateTimeToBytesConverter offsetToBytes, BytesToOffsetDateTimeConverter bytesToOffset) {
//		return new RedisCustomConversions(Arrays.asList(offsetToBytes, bytesToOffset));
//	}

}
