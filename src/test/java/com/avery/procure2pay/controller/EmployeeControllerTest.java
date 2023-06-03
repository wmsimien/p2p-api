package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private EmployeeService employeeService;

    // create standard Employee records
    Employee EMPLOYEE_1 = new Employee(1L, "Patty", "Paul", "Procurement", "Buyer", "patty.paul@gmail.com", "");

    Employee EMPLOYEE_2 = new Employee(2L, "Hattie", "Monroe", "Procurement", "Manager", "hattie.monroe@gmail.com", "87932-002");

    @Test
    @DisplayName("get a list of all employees successfull")
    void getAllEmployees_success() throws Exception {
        // build data to retrieve
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));
        // test whether employee service returns two employees api/employees/
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        // build endpoint that will return APPLICATION JSON
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("get an employee by id successfully")
    void getEmployeeById_success() throws Exception {
        // test service
        when(employeeService.getEmployeeById(EMPLOYEE_2.getId())).thenReturn(Optional.of(EMPLOYEE_2));
        // test endpoint api/employees/2/
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/{employeeId}/", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(EMPLOYEE_2.getId()))
                .andExpect(jsonPath("$.data.firstName").value(EMPLOYEE_2.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(EMPLOYEE_2.getLastName()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("create an employee record successfully")
    void createEmployee_success() throws Exception {
        // test service
        when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(EMPLOYEE_1);
        // build mock request to test endpoint api/employees/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(EMPLOYEE_1));
        // make mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(EMPLOYEE_1.getId()))
                .andExpect(jsonPath("$.data.firstName").value(EMPLOYEE_1.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(EMPLOYEE_1.getLastName()))
                .andExpect(jsonPath("$.data.department").value(EMPLOYEE_1.getDepartment()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("update an employee by return 404")
    void updateEmployeeById_recordNotFound() throws Exception {
        // create employee records
        Employee newEmployee = new Employee("Katty", "Maul", "Procurement", "Buyer", "katty.maul@gmail.com", "");
        // test service
        when(employeeService.updateEmployeeById(anyLong(), Mockito.any(Employee.class))).thenReturn(Optional.empty());
        // build mock request to test endpoint api/employees/1/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/employees/{employeeId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        // make mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find employee with id 1"))
                .andDo(print());
    }

    @Test
    @DisplayName("update an employee by return 200")
    void updateEmployeeById_success() throws Exception {
        // create employee records
        Employee newEmployee = new Employee("Katty", "Maul", "Procurement", "Buyer", "katty.maul@gmail.com", "");
        Employee UpdatedEmployee = new Employee("UpdatedKatty", "UpdatedMaul", "Procurement", "Buyer", "katty.maul@gmail.com", "");
        // test repository
        when(employeeService.updateEmployeeById(anyLong(), Mockito.any(Employee.class))).thenReturn(Optional.of(UpdatedEmployee));
        // build mock request to test endpoint api/employees/1/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/employees/{employeeId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newEmployee));
        // make mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(UpdatedEmployee.getId()))
                .andExpect(jsonPath("$.data.firstName").value(UpdatedEmployee.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(UpdatedEmployee.getLastName()))
                .andExpect(jsonPath("$.data.department").value(UpdatedEmployee.getDepartment()))
                .andExpect(jsonPath("$.message").value("employee with id 1 has been successfully updated"))
                .andDo(print());
    }

}