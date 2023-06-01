package com.avery.procure2pay.service;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /**
     * Method calls on the employee repository to handle the updating of the employee record.
     * @param employeeId Specific employee id to update
     * @param employeeObject Specific employee record data to update
     * @return Update employee record on success or calls InformationNotFoundException() when specified employee is not found.
     * @throws InformationNotFoundException Throws a message that the specified employee id cannot be found.
     */
    public Optional<Employee> updateEmployeeById(Long employeeId, Employee employeeObject) throws InformationNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employee.get().setFirstName(employeeObject.getFirstName());
            employee.get().setLastName(employeeObject.getLastName());
            employee.get().setDepartment(employeeObject.getDepartment());
            employee.get().setRole(employeeObject.getRole());
            employee.get().setEmail(employeeObject.getEmail());
            employee.get().setGl_acct_no(employeeObject.getGl_acct_no());
            employeeRepository.save(employee.get());
            return employee;
        } else {
            throw new InformationNotFoundException("cannot find employee with id " + employeeId);

        }
    }
}
