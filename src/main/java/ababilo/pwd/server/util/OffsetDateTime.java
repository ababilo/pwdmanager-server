package ababilo.pwd.server.util;

import java.util.Date;

/**
 * Created by ababilo on 18.11.16.
 */
public class OffsetDateTime {

    private Date dateTime;
    private String offset;

    public OffsetDateTime(java.time.OffsetDateTime offsetDateTime) {
        this.dateTime = new Date(offsetDateTime.toInstant().toEpochMilli());
        this.offset = offsetDateTime.toOffsetTime().getOffset().toString();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getOffset() {
        return offset;
    }
}
