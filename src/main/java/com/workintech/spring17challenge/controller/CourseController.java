package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.model.*;
import com.workintech.spring17challenge.validation.courseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RequestMapping("/courses")
@RestController
public class CourseController {

    private List<Course> courses;
    private LowCourseGpa lowCourseGpa;
    private HighCourseGpa highCourseGpa;
    private MediumCourseGpa mediumCourseGpa;


    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
    }

    @Autowired

    public CourseController( LowCourseGpa lowCourseGpa,HighCourseGpa highCourseGpa,
                             MediumCourseGpa mediumCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.highCourseGpa = highCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
    }

@GetMapping
    public List<Course> allCourses(){
    return courses;
}
@GetMapping("/{name}")
    public Course findCourse(@PathVariable String name) {
    courseValidation.checkNameExistence(courses, name);
    for (Course course : courses) {
        if (course.getName().equals(name)) {
            return course;
        }
    }
        throw new ResponseStatusException( HttpStatus.NOT_FOUND, "bu name ye sahip yok " + name);
}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@RequestBody Course course) {

        courseValidation.isCreditValid(course.getCredit());

        int totalGpa = calculateTotalGpa(course);

        System.out.println("Total GPA for " + course.getName() + ": " + totalGpa);

        // Add course to the list
        courses.add(course);

        return course;
    }

    // sor bunu
@PutMapping("/{id}")
public Course updateCourse(@PathVariable int id , @RequestBody Course course){

        if(id < 0 || id >= courses.size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"böyle bir id courses yok ");
        }
        for(Course c : courses){
            if(c.getName().equals(course.getName()) && courses.indexOf(c) != id){
throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bu isimde course mevcut" + course.getName());
            }
        }
      courses.set(id,course);

        return courses.get(id);

}

    private int calculateTotalGpa(Course course) {
        int gpaCoefficient = course.getGrade().getCoefficient();
        int credit = course.getCredit();

        if (credit <= 2) {
            return gpaCoefficient * credit * lowCourseGpa.getGpa();
        } else if (credit == 3) {
            return gpaCoefficient * credit * mediumCourseGpa.getGpa();
        } else {
            return gpaCoefficient * credit * highCourseGpa.getGpa();
        }
    }


    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable int id){
        if(id < 0 || id >= courses.size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"böyle bir id yok ");
        }
        return courses.remove(id);

    }
}
