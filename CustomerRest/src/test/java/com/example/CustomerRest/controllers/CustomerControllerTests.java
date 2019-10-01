package com.example.CustomerRest.controllers;

import com.example.CustomerRest.entities.Customer;
import com.example.CustomerRest.repositories.CustomerRepository;
import com.example.CustomerRest.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    SimpleDateFormat formatter;
    List<Customer> customers;

    @Before
    public void setup() throws ParseException {
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        customers = new ArrayList();
        customers.add(new Customer("Sally", formatter.parse("01/04/2019")));
        customers.add(new Customer("John", formatter.parse("01/05/2019")));
        customers.add(new Customer("Mary", formatter.parse("01/06/2019")));
        customerRepository.saveAll(customers);
    }
    @Test
    public void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sally"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].name").value("Mary"));
    }

    @Test
    public void getOneCustomer() throws Exception{
        Long id = customers.get(0).getId();
        mockMvc.perform(get("/customer/" + id))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Sally"));
    }

    @Test
    public void createCustomer() throws Exception{
        mockMvc.perform(post("customer").contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\" : \"Julie\", \"date\":\"11/24/2019\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Julie"));
    }

    @Test
    public void getCustomerByName() throws Exception{
        mockMvc.perform(get("/customer?name=Sally"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sally"));
            }

     @Test
     public void getCustomerByDate() throws Exception{
        mockMvc.perform(get("/customer/date?date=01/04/2019"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sally"));
    }

    @Test
    public void addCustomer() throws Exception{
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"name\":\"Molly\",\"date\":\"11/24/2019\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Molly"));
   }

   @Test
    public void updateCustomer() throws Exception{
        mockMvc.perform(put("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"name\":\"Molly\",\"date\":\"11/12/2019\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Molly"));

   }

   @Test
    public void deleteCustomer() throws Exception{
        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isOk());
   }

}
