package org.sergei.parser.model;

import javax.persistence.*;

/**
 * @author Sergei Visotsky, 2018
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "posit")
    private String position;

    @Column(name = "experience")
    private int experience;

    public Employee() {
    }

    public Employee(String firstName, String lastName, int age,
                    String position, int experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
        this.experience = experience;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
