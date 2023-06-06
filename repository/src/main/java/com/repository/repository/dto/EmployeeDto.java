package com.repository.repository.dto;

import com.repository.repository.entities.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto
{
    public EmployeeDto(Employee employee)
    {
        this.name = employee.getName();
        this.lastName = employee.getLastName();
        this.patronymic = employee.getPatronymic();
        this.birthday = employee.getBirthday();
        this.iconName = employee.getIconName();

        this.posts = employee.getPosts().stream().map(Post::getName).collect(Collectors.toSet());
        this.room = employee.getRoom().toString();

        Optional.ofNullable(employee.getPhoneNumber()).ifPresent(pn -> this.phoneNumber = pn.toString());

        this.department = employee.getDepartment().toString();
    }

    public EmployeeDto(String name, String lastName, String patronymic)
    {
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    private String name;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private String iconName;
    private Set<String> posts;
    private String phoneNumber;
    private String room;
    private String department;
}
