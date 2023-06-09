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
            Employee employee1 = new Employee("Thomas", "Carter", "Sales", "Staff", "thomas.hotmail@gmail.com", "");
            Employee employee2 = new Employee("Connie", "Jones", "Corporate", "Staff", "connie.jones@gmail.com", "");
            Employee employee3 = new Employee("John", "Blake", "Procurement", "Buyer", "thomas.blake@gmail.com", "878787-001");

            Employee employee4 = new Employee("Andy", "Hovancik", "Procurement", "Buyer", "andy.hovancik@gmail.com", "876985-003");
            Employee employee5 = new Employee("Chaz", "Jones", "Procurement", "Buyer", "chaz.jones@gmail.com", "");
            Employee employee6 = new Employee("Robert", "Wick", "Procurement", "Buyer", "robert.wick@gmail.com", "878687-011");

            // save employee records
            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);
            employeeRepository.save(employee4);
            employeeRepository.save(employee5);
            employeeRepository.save(employee6);
        }
        logger.info("Count of seeded employee records from EmployeeDataLoader:  " + employeeRepository.count());
    }
}
