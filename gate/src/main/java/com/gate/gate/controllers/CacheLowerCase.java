package com.gate.gate.controllers;

import org.antlr.v4.runtime.misc.Pair;
import dto.EmployeeDto;

import java.util.LinkedList;
import java.util.List;

public class CacheLowerCase
{
    private final Pair<String, List<EmployeeDto>> cache;
    private final boolean isInitialize;

    public CacheLowerCase(String query, List<EmployeeDto> employees)
    {
        cache = new Pair<>(query.toLowerCase(), employees);
        isInitialize = true;
    }

    public CacheLowerCase()
    {
        cache = new Pair<>("", new LinkedList<>());
        isInitialize = false;
    }

    public boolean hasDataForQuery(final String query)
    {
        if(query == null || !isInitialize)
            return false;

        return query.toLowerCase().startsWith(cache.a);
    }

    public List<EmployeeDto> extractDataForQuery(final String query)
    {
        final String lowerQuery = query.toLowerCase();

        return cache.b.stream().filter(employeeDto ->

                employeeDto.getName().toLowerCase().startsWith(lowerQuery) ||
                employeeDto.getLastName().toLowerCase().startsWith(lowerQuery) ||
                employeeDto.getPatronymic().toLowerCase().startsWith(lowerQuery)

        ).toList();
    }

}


