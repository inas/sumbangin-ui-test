package com.example.android.sumbangin_android;

import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserInputTest {
    private UserSerializer userInput;

    @Test
    public void testConstractor(){
        userInput = new UserSerializer("ppl", "ppl@ppl.com", "123123",
                "+62811111111");
        assertEquals("ppl", userInput.getName());
        assertEquals("ppl@ppl.com", userInput.getEmail());
        assertEquals("123123", userInput.getPassword());
        assertEquals("+62811111111", userInput.getPhone());
    }

    @Test
    public void testConstractor2(){
        userInput = new UserSerializer("ppl", "ppl@ppl.com", "123123", "+62811111111");
        assertEquals("ppl", userInput.getName());
        assertEquals("ppl@ppl.com", userInput.getEmail());
        assertEquals("123123", userInput.getPassword());
        assertEquals("+62811111111", userInput.getPhone());
    }

    @Test
    public void testConstractor3(){
        userInput = new UserSerializer("ppl@ppl.com", "123123");
        assertEquals("ppl@ppl.com", userInput.getEmail());
        assertEquals("123123", userInput.getPassword());
    }

    @Test
    public void testGetName(){
        userInput = new UserSerializer();
        userInput.setName("ppl");
        assertEquals("ppl", userInput.getName());
    }

    @Test
    public void testSetName(){
        userInput = new UserSerializer();
        userInput.setName("ppl");
        assertEquals("ppl", userInput.getName());
    }

    @Test
    public void testGetEmail(){
        userInput = new UserSerializer();
        userInput.setEmail("ppl@ppl.com");
        assertEquals("ppl@ppl.com", userInput.getEmail());
    }

    @Test
    public void testSetEmail(){
        userInput = new UserSerializer();
        userInput.setEmail("ppl@ppl.com");
        assertEquals("ppl@ppl.com", userInput.getEmail());
    }

    @Test
    public void testGetPassword(){
        userInput = new UserSerializer();
        userInput.setPassword("123123");
        assertEquals( "123123", userInput.getPassword());
    }

    @Test
    public void testSetPassword(){
        userInput = new UserSerializer();
        userInput.setPassword("123123");
        assertEquals( "123123", userInput.getPassword());
    }

    @Test
    public void testGetPhone(){
        userInput = new UserSerializer();
        userInput.setPhone("+62811111111");
        assertEquals("+62811111111", userInput.getPhone());
    }

    @Test
    public void testSetPhone(){
        userInput = new UserSerializer();
        userInput.setPhone("+62811111111");
        assertEquals("+62811111111", userInput.getPhone());
    }

    @Test
    public void testSetAddress(){
        userInput = new UserSerializer();
        AddressSerializer address =
                new AddressSerializer("Fasilkom UI", "", "", "", "");
        userInput.setAddress(address);
        assertEquals("Fasilkom UI", userInput.getAddress().getAlamat());
    }

    @Test
    public void testGetToken(){
        userInput = new UserSerializer();
        userInput.setToken("abcdefghijklmnopqrstuvwxyz");
        assertEquals("abcdefghijklmnopqrstuvwxyz", userInput.getToken());
    }

    @Test
    public void testSetToken(){
        userInput = new UserSerializer();
        userInput.setToken("abcdefghijklmnopqrstuvwxyz");
        assertEquals("abcdefghijklmnopqrstuvwxyz", userInput.getToken());
    }

    @Test
    public void testGetId() {
        userInput = new UserSerializer();
        userInput.setId(2);
        assertEquals(2, userInput.getId());
    }

    @Test
    public void testSetId() {
        userInput = new UserSerializer();
        userInput.setId(2);
        assertEquals(2, userInput.getId());
    }

    @Test
    public void testGetPhoto() {
        userInput = new UserSerializer();
        userInput.setId(2);
        assertEquals(2, userInput.getId());
    }

    @Test
    public void testSetPhoto() {
        userInput = new UserSerializer();
        userInput.setPhoto("http://www.google.com");
        assertEquals("http://www.google.com", userInput.getPhoto());
    }
}