package com.avery.procure2pay.service;

import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest
class EmployeeServiceTest {

    Logger logger = Logger.getLogger(EmployeeServiceTest.class.getName());

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired EmployeeService employeeService;


    Employee EMPLOYEE_1 = new Employee(1L, "Patty", "Paul", "Procurement", "Buyer", "patty.paul@gmail.com", "");

    Employee EMPLOYEE_2 = new Employee(2L, "Hattie", "Monroe", "Procurement", "Manager", "hattie.monroe@gmail.com", "87932-002");

    @Test
    @DisplayName("get a list of all employees")
    void getAllEmployees() {
        List<Employee> employeeLists = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
        // mock employeeRepository findAll()
        when(employeeRepository.findAll()).thenReturn(employeeLists);

        List<Employee> employeeList = employeeService.getAllEmployees();
        Assertions.assertEquals(2, employeeList.size());
    }

    @Test
    @DisplayName("get a employee by id")
    void getEmployeeById() {
        // mock employeeRepository findById()
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(EMPLOYEE_2));

        Optional<Employee> employee = employeeService.getEmployeeById(2L);

        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(2, employee.get().getId());
    }

    @Test
    @DisplayName("create an employee record")
    void createEmployee() {
        Employee newEmployee = new Employee("Carol", "Thomas", "Sales", "Manager", "carol.thomas@gmail.com", "");
        // mock employeeRepository save()
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(newEmployee);

        Employee createdEmployee = employeeService.createEmployee(newEmployee);

        when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(newEmployee);

        Assertions.assertNotNull(createdEmployee);
        assertEquals(newEmployee.getFirstName(), createdEmployee.getFirstName());
        assertEquals(newEmployee.getLastName(), createdEmployee.getLastName());
    }

    @Test
    @DisplayName("update a specific employee record successfully")
    void updateEmployeeById() {
        Employee employee = new Employee(1L,"Carol", "Thomas", "Sales", "Manager", "carol.thomas@gmail.com", "");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee employeeObject = new Employee("UpdateCarol", "UpdatedThomas", "Sales", "Manager", "updatedcarol.updatedthomas@gmail.com", "");
        Optional<Employee> updatedEmployee = employeeService.updateEmployeeById(1L, employeeObject);

        Assertions.assertEquals(updatedEmployee.get().getFirstName(), employeeObject.getFirstName());
        Assertions.assertEquals(updatedEmployee.get().getEmail(), employeeObject.getEmail());
    }
}