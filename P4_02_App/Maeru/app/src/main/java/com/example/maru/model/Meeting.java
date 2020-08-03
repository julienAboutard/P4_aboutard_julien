package com.example.maru.model;

import java.util.Date;
import java.util.Objects;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /** Identifier */
    private long id;

    /** Full Date */
    private Date date;

    /** Location */
    private String location;

    /** meetingSubject */
    private String meetingSubject;

    /** List of email*/
    private Email email;

    public Meeting(long id, Date date, String location, String meetingSubject, Email email) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.meetingSubject = meetingSubject;
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
