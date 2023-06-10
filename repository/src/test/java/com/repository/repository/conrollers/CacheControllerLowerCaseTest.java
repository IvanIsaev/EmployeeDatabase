package com.repository.repository.conrollers;

import com.repository.repository.dto.EmployeeDto;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CacheControllerLowerCaseTest
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

    CacheControllerLowerCase cacheControllerLowerCase = new CacheControllerLowerCase();

    @Test
    void hasDataForQuery()
    {
        final String template0 = "А";
        List<EmployeeDto> employeesA = prepareData().stream().filter(employeeDto -> {

            return employeeDto.getName().toLowerCase().startsWith(template0.toLowerCase()) ||
                    employeeDto.getLastName().toLowerCase().startsWith(template0.toLowerCase()) ||
                    employeeDto.getPatronymic().toLowerCase().startsWith(template0.toLowerCase());}).toList();

        cacheControllerLowerCase.refresh(template0, employeesA);

        assertThat(cacheControllerLowerCase.hasDataForQuery(template0)).isEqualTo(true);
        assertThat(cacheControllerLowerCase.hasDataForQuery("В")).isEqualTo(false);
        assertThat(cacheControllerLowerCase.hasDataForQuery("Ал")).isEqualTo(true);
    }

    @Test
    void extractDataForQuery()
    {
        final String template = "Ал";
        List<EmployeeDto> employees = prepareData();
        cacheControllerLowerCase.refresh("", employees);

        assertThat(cacheControllerLowerCase.extractDataForQuery(template.toLowerCase())).hasSize(2);
    }
}