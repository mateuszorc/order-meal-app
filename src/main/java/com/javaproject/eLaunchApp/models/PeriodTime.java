package com.javaproject.eLaunchApp.models;

import com.javaproject.eLaunchApp.validator.PeriodTimeConstraint;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@PeriodTimeConstraint
@Embeddable
@GeneratePojoBuilder
public class PeriodTime {

    @Nullable
    private LocalTime begin;

    @Nullable
    private LocalTime end;

    @Nullable
    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalTime bigin) {
        this.begin = bigin;
    }

    @Nullable
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalTime end) {
        this.end = end;
    }
}
