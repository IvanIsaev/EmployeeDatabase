package com.repository.repository.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TBL_EMPLOYEES")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id") // Так как данный класс
// в качестве полей содержит сложные объекты PhoneNumber, Room, которые в свою очередь содержат так же
// ссылки на объект типа Employee, то при сериализации возникает циклическая зависимость и программа
// вылетает с исключением Failure while trying to resolve exception [org.springframework.http.converter.HttpMessageNotWritableException]
public class Employee implements Serializable
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "icon_name")
    private String iconName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_employee_post",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number_id", referencedColumnName = "id")
    private PhoneNumber phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}

