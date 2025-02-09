package com.workintech.spring17challenge.model;

import com.workintech.spring17challenge.entity.CourseGpa;
import org.springframework.stereotype.Component;

@Component
public class HighCourseGpa implements CourseGpa {

    @Override
    public int getGpa() {
        return 10;
    }
}
