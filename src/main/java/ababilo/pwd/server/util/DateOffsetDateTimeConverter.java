package ababilo.pwd.server.util;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by ababilo on 18.11.16.
 */
public class DateOffsetDateTimeConverter implements Converter<java.time.OffsetDateTime, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(java.time.OffsetDateTime date) {
        return new OffsetDateTime(date);
    }
}
