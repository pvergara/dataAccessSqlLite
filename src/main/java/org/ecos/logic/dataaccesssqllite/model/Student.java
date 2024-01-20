package org.ecos.logic.dataaccesssqllite.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Student {
    private final int studentId;
    private final String name;
    private final String lastname;
    private final int height;
    private final int classId;

    public Student(int studentId, String name, String lastname, int height, int classId) {
        this.studentId = studentId;
        this.name = name;
        this.lastname = lastname;
        this.height = height;
        this.classId = classId;
    }
}
