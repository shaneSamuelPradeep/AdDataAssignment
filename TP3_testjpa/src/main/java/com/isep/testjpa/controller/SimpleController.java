package com.isep.testjpa.controller;

import com.isep.testjpa.repository.EmpRepository;
import com.isep.testjpa.model.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SimpleController {

    @Autowired
    private EmpRepository empRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello(@Param("name") String name) {
        return "Hello " + name;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Emp> getEmployees() {
        return empRepository.findAll();
    }
    @PostMapping(value="/employees")
    public Emp addEmployee(@RequestBody Emp emp) {
        return empRepository.save(emp);
    }

    // Get employee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Emp> getEmployeeById(@PathVariable Long id) {
        Optional<Emp> employee = empRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Emp> updateEmployee(@PathVariable Long id, @RequestBody Emp empDetails) {
        Optional<Emp> employeeOptional = empRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Emp employee = employeeOptional.get();
        employee.setEname(empDetails.getEname());
        employee.setEfirst(empDetails.getEfirst());
        employee.setJob(empDetails.getJob());
        // Update other fields as needed

        Emp updatedEmployee = empRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
    // Delete employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Optional<Emp> employeeOptional = empRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        empRepository.delete(employeeOptional.get());
        return ResponseEntity.ok().build();
    }

}
