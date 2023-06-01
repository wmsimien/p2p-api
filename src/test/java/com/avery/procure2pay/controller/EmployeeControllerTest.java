package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    // create standard Employee records
    Employee EMPLOYEE_1 = new Employee(1L, "Patty", "Paul", "Procurement", "Buyer", "patty.paul@gmail.com");

    Employee EMPLOYEE_2 = new Employee(2L, "Hattie", "Monroe", "Procurement", "Manager", "hattie.monroe@gmail.com");

    @Test
    void getAllEmployees_success() throws Exception {
        // build data to retrieve
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));

        // test whether repository returns two employees
        when(employeeRepository.findAll()).thenReturn(employeeList);
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
    void getEmployeeById_success() throws Exception {
        // repository
        when(employeeRepository.findById(EMPLOYEE_2.getId())).thenReturn(Optional.of(EMPLOYEE_2));
        // test endpoint api/employees/2
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/{employeeId}", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(EMPLOYEE_2.getId()))
                .andExpect(jsonPath("$.data.firstName").value(EMPLOYEE_2.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(EMPLOYEE_2.getLastName()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }
}