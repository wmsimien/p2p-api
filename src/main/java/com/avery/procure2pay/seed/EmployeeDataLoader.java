package com.avery.procure2pay.seed;

import com.avery.procure2pay.model.Employee;

import com.avery.procure2pay.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EmployeeDataLoader implements CommandLineRunner {

    Logger logger = Logger.getLogger(EmployeeDataLoader.class.getName());
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        loadEmployeeData();
    }

    private void loadEmployeeData() {
        if (employeeRepository.count() == 0) {
            // create employee records
            Employee employee1 = new Employee("Cindy", "Smith", "Sales", "Rep", "cindy.smith@gmail.com", "");
            Employee employee2 = new Employee("Connie", "Jones", "Cooperate", "Receptionist", "connie.jones@gmail.com", "");
            Employee employee3 = new Employee("Timmy", "Blue", "Facilities", "Manager", "timmy.blue@gmail.com", "878787-001");
            // save employee records
            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);
        }
logger.info("count of employeeRepository:  " + employeeRepository.count());
    }
}
