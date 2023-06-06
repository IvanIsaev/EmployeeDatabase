package com.repository.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TBL_POSTS")
@Getter
@Setter
@NoArgsConstructor
public class Post implements Serializable
{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name= "post", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Post(int id)
    {
        this.id = id;
    }
}

