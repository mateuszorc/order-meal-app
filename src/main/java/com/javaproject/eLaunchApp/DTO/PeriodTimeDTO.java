package com.javaproject.eLaunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.validator.PeriodTimeConstraint;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@PeriodTimeConstraint
@Embeddable
@GeneratePojoBuilder
public class PeriodTimeDTO {
    public static class View{
        public interface Basic{}
    }


    @JsonView(View.Basic.class)
    @Nullable
    private LocalTime begin;

    @JsonView(View.Basic.class)
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
