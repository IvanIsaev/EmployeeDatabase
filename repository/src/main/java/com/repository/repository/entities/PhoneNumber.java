package com.repository.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TBL_PHONE_NUMBERS")
@Getter
@Setter
@NoArgsConstructor
public class PhoneNumber
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "phoneNumber", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @Override
    public String toString()
    {
        return phoneNumber;
    }
}
