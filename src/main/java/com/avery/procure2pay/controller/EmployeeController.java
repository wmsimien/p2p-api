package com.avery.procure2pay.controller;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.repository.EmployeeRepository;
import com.avery.procure2pay.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    EmployeeService employeeService;

    // handles custom messages
    static HashMap<String, Object> message = new HashMap<>();

    /**
     * Method obtains listing of all employees
     * @return ResponseEntity to configure HTTP response to be NOT_FOUND when no employees are found and OK when employee results are found.
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
     * Methods obtains the specified employee by id.
     * @param employeeId Argument taken from url path.
     * @return Response of HttpStatus OK when specified employee record is found and HttpStatus of NOT_FOUND when employee of the specified id is not found.
     */
    @GetMapping(path="/employees/{employeeId}/")
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

    /**
     * Method creates a new employee record.
     * @return Response of HttpStatus CREATED when new employee record is created and HttpStatus of OK when a new employee record cannot be created.
     */
    @PostMapping("/employees/")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        if (newEmployee != null){
            message.put("message", "success");
            message.put("data", newEmployee);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create an employee at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Method update employee record based on specified employee id
     * @param employeeId Employee id to update
     * @param employeeObject Employee data to update
     * @return Response of HttpStatus OK when specified employee record is updated successfully and HttpStatus of NOT_FOUND when specified employee record is not found.
     */
    @PutMapping(path="/employees/{employeeId}/")
    public ResponseEntity<?> updateEmployeeById(@PathVariable(value="employeeId") Long employeeId, @RequestBody Employee employeeObject) throws InformationNotFoundException {
        Optional<Employee> employeeToUpdate = employeeService.updateEmployeeById(employeeId, employeeObject);
        if (employeeToUpdate.isEmpty()) {
            message.put("message", "cannot find employee with id " + employeeId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "employee with id " + employeeId + " has been successfully updated");
            message.put("data", employeeToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

}
