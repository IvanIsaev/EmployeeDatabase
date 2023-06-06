package com.repository.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TBL_DEPARTMENTS")
@Getter
@Setter
@NoArgsConstructor
public class Department
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "department")
    private String department;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Department(int id, final String department)
    {
        this.id = id;
        this.department = department;
    }

    @Override
    public String toString()
    {
        return department;
    }
}
