package com.repository.repository.repositories;

import com.repository.repository.entities.Department;
import com.repository.repository.entities.Employee;
import com.repository.repository.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<Employee, Integer>
{
    @Query(value = "SELECT u FROM Employee u WHERE u.room = :room")
    List<Employee> findByRoom(@Param("room") Room room);

    @Query(value = "SELECT u FROM Employee u Where u.department = :dept")
    List<Employee> findByDepartment(@Param("dept") Department department);

    @Query(value = "SELECT u FROM Employee u WHERE LOWER(u.lastName) LIKE :template%" +
            " OR LOWER(u.name) LIKE :template%" +
            " OR LOWER(u.patronymic) LIKE :template%")
    List<Employee> findByTemplateLike(@Param("template") String template);

    @Modifying
    @Query("delete from Employee u where u.id=:id")
    void deleteEmployee(@Param("id") Integer id);
}
