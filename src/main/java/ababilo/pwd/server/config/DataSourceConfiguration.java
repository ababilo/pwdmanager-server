package ababilo.pwd.server.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Collections;

/**
 * Created by ababilo on 08.11.16.
 */
@Configuration
@EnableAutoConfiguration
public class DataSourceConfiguration {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClientOptions options = MongoClientOptions.builder().
                socketTimeout(60000).
                connectTimeout(1200000).build();
        MongoCredential credentia = MongoCredential.createScramSha1Credential(
                "pwduser",
                "pwd",
                "pwd123".toCharArray()
        );
        return new SimpleMongoDbFactory(
                new MongoClient(new ServerAddress("localhost", 27017), Collections.singletonList(credentia), options),
                "pwd"
        );
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
