package com.javaproject.eLaunchApp.models;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class PeriodTime {

    @Nullable
    private LocalTime bigin;

    @Nullable
    private LocalTime end;

    @Nullable
    public LocalTime getBigin() {
        return bigin;
    }

    public void setBigin(@Nullable LocalTime bigin) {
        this.bigin = bigin;
    }

    @Nullable
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalTime end) {
        this.end = end;
    }
}
