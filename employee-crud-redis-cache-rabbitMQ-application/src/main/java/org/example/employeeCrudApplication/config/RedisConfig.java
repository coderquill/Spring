package org.example.employeeCrudApplication.config;

import java.time.Duration;

import org.example.employeeCrudApplication.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	
	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;
    
    
     @Bean
     JedisConnectionFactory jedisConnectionFactory() {
         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
         redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
         return new JedisConnectionFactory(redisStandaloneConfiguration);
     }
     
     @Bean
     public RedisTemplate redisTemplate() {
         RedisTemplate template = new RedisTemplate<>();
         template.setConnectionFactory(jedisConnectionFactory());
         template.setKeySerializer(new StringRedisSerializer());
         //template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
         template.setValueSerializer(new Jackson2JsonRedisSerializer<Employee>(Employee.class));
         return template;
     }
     
     @Autowired
     private CacheManager cacheManager;

     @EventListener
     public void onApplicationEvent(ApplicationReadyEvent event) {
         cacheManager.getCacheNames()
                     .parallelStream()
                     .forEach(n -> cacheManager.getCache(n).clear());
     }
}

    /* @Bean
     RedisTemplate< String, Object > redisTemplate() {
      final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
      template.setConnectionFactory( jedisConnectionFactory() );
      template.setKeySerializer( new StringRedisSerializer() );
      template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
      template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
      return template;
     }*/
	
	/*@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Employee>(Employee.class));
        return template;
    }
    
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory  factory) {
        RedisCacheConfiguration config =  RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10));
        RedisCacheManager cacheManager =  RedisCacheManager.builder(factory).cacheDefaults(config).build();
        return cacheManager;
    }
    /*
     <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
     * */
    




