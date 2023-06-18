package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto
{
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
