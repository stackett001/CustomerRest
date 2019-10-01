package com.example.CustomerRest.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTests {
    Customer customer;

    @Before
    public void setup(){
        customer = new Customer();
    }
    @Test
    public void doesExist(){
        assertNotNull(new Customer());
    }

    @Test
    public void name(){
        customer.setName("Bob");
        assertEquals("Bob", customer.getName());
    }

    @Test
    public void date() throws ParseException {
        customer.setDate("01/22/2019");
        assertEquals("01/22/2019", customer.getDate());
    }
}
