package com.repository.repository.conrollers;

import dto.EmployeeDto;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class CacheControllerLowerCase
{
    private Pair<String, List<EmployeeDto>> cache;

    public boolean hasDataForQuery(final String query)
    {
        if(cache == null || query == null)
            return false;

        return query.toLowerCase().startsWith(cache.a);
    }

    public List<EmployeeDto> extractDataForQuery(final String query)
    {
        return cache.b.stream().filter(employeeDto -> {

            return employeeDto.getName().toLowerCase().startsWith(query.toLowerCase()) ||
                   employeeDto.getLastName().toLowerCase().startsWith(query.toLowerCase()) ||
                   employeeDto.getPatronymic().toLowerCase().startsWith(query.toLowerCase());
        }).toList();
    }

    public void refresh(String query, List<EmployeeDto> employees)
    {
        cache = new Pair<>(query.toLowerCase(), employees);
    }
}


