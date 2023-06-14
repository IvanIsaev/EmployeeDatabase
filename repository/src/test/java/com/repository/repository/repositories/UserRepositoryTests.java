package com.repository.repository.repositories;

import com.repository.repository.dto.DepartmentDto;
import com.repository.repository.dto.RoomDto;
import com.repository.repository.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTests
{

	@Autowired
	UserRepository userRepository;

	@Test
	void findAllTest()
	{
		assertThat(userRepository.findAll()).hasSize(17);
	}

	@Test
	void findEmployeesBySubstringTest()
	{
		final String template = "Але";

		List<Employee> employees_after_mask = userRepository.findByTemplateLike(template.toLowerCase());

		assertThat(employees_after_mask).hasSize(4);
	}

	@Test
	void findEmployeesByRoomTest()
	{
		Map<RoomDto, Integer> roomAndCountPeopleHere = new HashMap<>();

		roomAndCountPeopleHere.put(new RoomDto(1), 5);
		roomAndCountPeopleHere.put(new RoomDto(2), 3);
		roomAndCountPeopleHere.put(new RoomDto(3), 7);
		roomAndCountPeopleHere.put(new RoomDto(4), 2);

		roomAndCountPeopleHere.forEach((room, countPeople)->{assertThat(userRepository.findByRoomId(room.getId())).hasSize(countPeople);});
	}

	@Test
	void findEmployeesByDepartmentTest()
	{
		DepartmentDto department = new DepartmentDto(1);

		List<Employee> employees = userRepository.findByDepartmentId(department.getId());

		assertThat(employees).hasSize(5);
	}

	@Test
	void moreThanOnePostsTest()
	{
		List<Employee> employees = userRepository.findAll();
		final int id_1 = 1;
		final int id_6 = 6;
		final int id_7 = 7;
		Set<Integer> id_s = Set.of(id_1, id_6, id_7);

		assertThat(employees.stream().filter(employee -> employee.getPosts().size() > 1).distinct()).hasSize(id_s.size());
		employees.stream().filter(employee -> employee.getPosts().size() > 1).distinct().forEach(employee -> assertThat(id_s.contains(employee.getId())));
	}

	@Test
	@Transactional
	void removeEmployeeTest()
	{
		final int id_for_delete = 1;
		int count_before_delete = userRepository.findAll().size();

		userRepository.deleteEmployee(id_for_delete);

		assertThat(userRepository.findAll()).hasSize(count_before_delete - 1);
	}

}
