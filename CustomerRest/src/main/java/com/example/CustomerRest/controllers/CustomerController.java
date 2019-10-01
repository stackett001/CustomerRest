package com.example.CustomerRest.controllers;

import com.example.CustomerRest.entities.Customer;
import com.example.CustomerRest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@RequestMapping("/customers")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    private Long id;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("customer/{id}")
    public Customer getOneCustomer(@PathVariable Long id){
        return customerService.getOneCustomer(id);
    }

    @RequestMapping("/customer")
    public Customer getCustomerByName(@RequestParam(value="name") String name){
        return customerService.getCustomerByName(name);
    }

    @RequestMapping("/customer/date")
    public Customer getCustomerByDate(@RequestParam(value="date") Date date){
        return customerService.getCustomerByDate(date);
    }

    @PostMapping("/customer")
    public Customer addNewCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customer")
    public Customer editCustomer(@RequestBody Customer customer){
        return customerService.editCustomer(customer);
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteByName(id);
    }

}