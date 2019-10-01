package com.example.CustomerRest.services;

import com.example.CustomerRest.entities.Customer;
import com.example.CustomerRest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    SimpleDateFormat formatter;

    @Before
    public void setup(){
        formatter = new SimpleDateFormat("MM/dd/yyyy");
    }
    @Test
    public void doesExist(){
        assertNotNull(customerService);
    }

    @Test
    public void getAllCustomers() throws ParseException {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Sally", formatter.parse("01/04/2019")));
        customers.add(new Customer("John", formatter.parse("01/05/2019")));
        customers.add(new Customer("Mary", formatter.parse("01/06/2019")));
        customerRepository.saveAll(customers);
        assertTrue(customerService.getAllCustomers().size() == 3);
    }

    @Test
    public void getOneCustomer() throws ParseException {
        Customer customer = new Customer("Joe", formatter.parse("01/04/2019"));
        customerRepository.save(customer);
        assertNotNull(customerService.getOneCustomer(customer.getId()));
    }

    @Test
    public void deleteOneCustomer() throws ParseException {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Sally", formatter.parse("01/04/2019")));
        customers.add(new Customer("John", formatter.parse("01/05/2019")));
        customers.add(new Customer("Mary", formatter.parse("01/06/2019")));
        customerRepository.saveAll(customers);
        customerService.deleteByName(customers.get(0).getId());
        assertTrue(customerService.getAllCustomers().size() == 2);
    }

    @Test
    public void createCustomer() throws ParseException {
        Customer customer = new Customer("Joe", formatter.parse("01/04/2019"));
        customerService.createCustomer(customer);
        assertEquals("Joe", customer.getName());
    }

    @Test
    public void editCustomer() throws ParseException {
        Customer customer = new Customer("Joe", formatter.parse("01/04/2019"));
        customerService.createCustomer(customer);
        customer.setName("John");
        customerService.editCustomer(customer);
        assertEquals("John", customer.getName());
    }

    @Test
    public void getAllCustomersTest() {
        for(Customer customer : customerRepository.findAll()){
            verify(customerService).getAllCustomers();
        }
     }

     @Test
    public void getCustomerByName()throws ParseException{
        List<Customer> customers = new ArrayList<>();
         customers.add(new Customer("Sally", formatter.parse("01/04/2019")));
         customers.add(new Customer("John", formatter.parse("01/05/2019")));
         customers.add(new Customer("Mary", formatter.parse("01/06/2019")));
         customerRepository.saveAll(customers);
         assertEquals("01/04/2019", customerService.getCustomerByName("Sally").getDate());
        }

     @Test
    public void getCustomerByDate() throws ParseException{
         List<Customer> customers = new ArrayList<>();
         customers.add(new Customer("Sally", formatter.parse("01/04/2019")));
         customers.add(new Customer("John", formatter.parse("01/05/2019")));
         customers.add(new Customer("Mary", formatter.parse("01/06/2019")));
         customerRepository.saveAll(customers);
         assertEquals("Sally", customerService.getCustomerByDate(formatter.parse("01/04/2019")).getName());
     }
}
