package com.gate.gate;

import com.gate.gate.controllers.CacheLowerCase;
import dto.EmployeeDto;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CacheLowerCaseTest
{
    private List<EmployeeDto> prepareData()
    {
        List<EmployeeDto> employees = new LinkedList<>();

        employees.add(new EmployeeDto("Виктор", "Конь", "Иванов"));
        employees.add(new EmployeeDto("Николай", "Викра", "Викторович"));
        employees.add(new EmployeeDto("Иван", "Пес", "Николаевич"));
        employees.add(new EmployeeDto("Виктория", "Кот", "Светикович"));
        employees.add(new EmployeeDto("Алеся", "Вик", "Ивановна"));
        employees.add(new EmployeeDto("Светлана", "Олень", "Мстиславовна"));
        employees.add(new EmployeeDto("Лана", "Жук", "Олеговна"));
        employees.add(new EmployeeDto("Елена", "Полено", "Геннадьевна"));
        employees.add(new EmployeeDto("Мстислав", "Карась", "Александрович"));
        employees.add(new EmployeeDto("Олег", "Голубь", "Анатольевич"));

        return employees;
    }

    CacheLowerCase cacheLowerCase;

    @Test
    void hasDataForQuery()
    {
        final String template0 = "А";
        List<EmployeeDto> employeesA = prepareData().stream().filter(employeeDto -> {

            return employeeDto.getName().toLowerCase().startsWith(template0.toLowerCase()) ||
                    employeeDto.getLastName().toLowerCase().startsWith(template0.toLowerCase()) ||
                    employeeDto.getPatronymic().toLowerCase().startsWith(template0.toLowerCase());}).toList();

        cacheLowerCase = new CacheLowerCase(template0, employeesA);

        assertThat(cacheLowerCase.hasDataForQuery(template0)).isEqualTo(true);
        assertThat(cacheLowerCase.hasDataForQuery("В")).isEqualTo(false);
        assertThat(cacheLowerCase.hasDataForQuery("Ал")).isEqualTo(true);
    }

    @Test
    void extractDataForQuery()
    {
        final String template = "Ал";
        List<EmployeeDto> employees = prepareData();
        cacheLowerCase = new CacheLowerCase("", employees);

        assertThat(cacheLowerCase.extractDataForQuery(template.toLowerCase())).hasSize(2);
    }
}