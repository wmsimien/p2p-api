package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/api")
public class EmployeeController {
    Logger logger = Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    private EmployeeRepository employeeRepository;

    // handles custom messages
    static HashMap<String, Object> message = new HashMap<>();

    /**
     *
     * @return
     */
    @GetMapping(path="/employees/")
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()){
            message.put("message", "cannot find any employees");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", employeeList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     *
     * @param employeeId
     * @return
     */
    @GetMapping(path="/employees/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value="employeeId") Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            message.put("message", "success");
            message.put("data", employee.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find employee with id " + employeeId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}
