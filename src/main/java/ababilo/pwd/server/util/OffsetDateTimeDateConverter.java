package ababilo.pwd.server.util;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;

/**
 * Created by ababilo on 18.11.16.
 */
public class OffsetDateTimeDateConverter implements Converter<OffsetDateTime, java.time.OffsetDateTime> {

    @Override
    public java.time.OffsetDateTime convert(OffsetDateTime offsetDateTime) {
        return java.time.OffsetDateTime.ofInstant(offsetDateTime.getDateTime().toInstant(), ZoneId.systemDefault());
    }
}
