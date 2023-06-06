package com.repository.repository.repositories;

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
		Map<Room, Integer> roomAndCountPeopleHere = new HashMap<>();

		roomAndCountPeopleHere.put(new Room(1, "101"), 5);
		roomAndCountPeopleHere.put(new Room(2, "102"), 3);
		roomAndCountPeopleHere.put(new Room(3, "103a"), 7);
		roomAndCountPeopleHere.put(new Room(4, "104a"), 2);

		roomAndCountPeopleHere.forEach((room, countPeople)->{assertThat(userRepository.findByRoom(room)).hasSize(countPeople);});
	}

	@Test
	void findEmployeesByDepartmentTest()
	{
		Department department = new Department();
		department.setId(1);

		List<Employee> employees = userRepository.findByDepartment(department);

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

// TODO: realize insert
//	@Test
//	@Transactional
//	void addEmployeeTest()
//	{
//		Department department = new Department();
//		department.setId(2);
//
//		PhoneNumber phoneNumber = new PhoneNumber();
//		phoneNumber.setId(3);
//
//		Set<Post> posts = new HashSet<>();
//		posts.add(new Post(7));
//		posts.add(new Post(12));
//
//		Room room = new Room();
//		room.setId(3);
//
//		Employee employee = new Employee();
//		employee.setName("Михаил");
//		employee.setLastName("Михайлов");
//		employee.setPatronymic("Михайлович");
//		employee.setId(18);
//		employee.setBirthday(new Date(1985, 10, 3));
//		employee.setDepartment(department);
//		employee.setRoom(room);
//		employee.setPhoneNumber(phoneNumber);
//		employee.setPosts(posts);
//
//		System.out.println(userRepository.findAll().size());

//		entityManager.persist(employee);

//		System.out.println(userRepository.findAll().size());
//	}

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
