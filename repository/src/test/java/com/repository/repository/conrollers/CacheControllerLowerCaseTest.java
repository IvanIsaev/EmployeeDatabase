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
        final String template = "Ви";

        assertThat(cacheControllerLowerCase.hasDataForQuery(template)).isEqualTo(false);

        List<EmployeeDto> employees = prepareData();

        cacheControllerLowerCase.refresh("", employees);

        assertThat(cacheControllerLowerCase.hasDataForQuery(template)).isEqualTo(true);
        assertThat(cacheControllerLowerCase.hasDataForQuery("Вик")).isEqualTo(true);

        List<EmployeeDto> templateEmployee = cacheControllerLowerCase.extractDataForQuery(template.toLowerCase());

        cacheControllerLowerCase.refresh(template, templateEmployee);

        final String failTemplate = "Mc";

        assertThat(cacheControllerLowerCase.hasDataForQuery(failTemplate.toLowerCase())).isEqualTo(false);
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