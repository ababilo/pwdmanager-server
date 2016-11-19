package ababilo.pwd.server.config;

import ababilo.pwd.server.util.DateOffsetDateTimeConverter;
import ababilo.pwd.server.util.OffsetDateTimeDateConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return new MongoTemplate(mongoDbFactory(), mongoConverter());
    }

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new DateOffsetDateTimeConverter());
        converters.add(new OffsetDateTimeDateConverter());
        return new CustomConversions(converters);
    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mongoConverter.setCustomConversions(customConversions());
        return mongoConverter;
    }
}
