package com.repository.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.repository.repository.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table( name = "TBL_ROOMS")
@Getter
@Setter
@NoArgsConstructor
public class Room
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "room")
    private String room;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @Override
    public String toString()
    {
        return room;
    }

    public Room(Integer id, String room)
    {
        this.id = id;
        this.room = room;
    }

    public Room(RoomDto roomDto)
    {
        this.id = roomDto.getId();
    }
}
