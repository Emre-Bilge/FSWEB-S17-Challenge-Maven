package com.workintech.spring17challenge.validation;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.entity.Course;
import org.springframework.http.HttpStatus;

import java.util.List;


public class courseValidation {

    public static void isCreditValid(Integer credit) {

        if (credit < 0 || credit > 4) {
            throw new ApiException("credit 0 dan  küçük veya 4 ten büyük olamaz" , HttpStatus.BAD_REQUEST   );
        }
    }

    public static void checkNameExistence(List<Course> courses, String name) {
        boolean found = false;
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                System.out.println("bu course mevcut devam edilebilir");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("course mevcut değil");
            throw new ApiException("böyle bir course mevcut değildir" + name , HttpStatus.BAD_REQUEST);

        }
    }
}
