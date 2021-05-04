package com.example.ingsw.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationControllerTest {

    final RegistrationController registrationController = new RegistrationController(null,"");

    /**TEST DI SUCCESSO**/


    @Test
    public void checkValidPasswordFieldStartingWithNumberEndingWithUppercase() throws Exception {
        assertTrue(registrationController.checkPasswordField("90PassworD"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithNumberEndingWithLowercase() throws Exception {
        assertTrue(registrationController.checkPasswordField("90PassworD"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithUppercaseEndingWithNumber() throws Exception {
        assertTrue(registrationController.checkPasswordField("Password90"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithUppercaseEndingWithLowercase() throws Exception {
        assertTrue(registrationController.checkPasswordField("Pass90word"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithLowercaseEndingWithNumber() throws Exception {
        assertTrue(registrationController.checkPasswordField("passWORD90"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithLowercaseEndingWithUppercase() throws Exception {
        assertTrue(registrationController.checkPasswordField("pass90WORD"));
    }

    @Test
    public void checkValidPasswordFieldContainingSpecialChars() throws Exception {
        assertTrue(registrationController.checkPasswordField("Password?^$40"));
    }

    @Test
    public void checkValidPasswordFieldContainingSixCases() throws Exception {
        assertTrue(registrationController.checkPasswordField("Pass98"));
    }


    /** TEST FALLIMENTARI **/



    @Test public void checkEmptyPasswordField(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField(""));
        assertEquals(exception.getMessage(),"Empty Password");
    }

    @Test public void checkEmptyPasswordFieldAndFullRePasswordField(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField(""));
        assertEquals(exception.getMessage(),"Empty Password");
    }

//
    @Test public void checkFullInvalidPasswordFieldWithoutLowercase(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("PASSWORD1"));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutUppercase(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("password1"));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutNumbers(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Password"));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutChars(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("999999"));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithOnlyUppercase(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("PASSWORD"));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithOnlyLowercase(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("password"));
        assertEquals(exception.getMessage(),"Weak Password");
    }



    @Test public void checkEmptyPasswordFieldWithSpaceChars(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("      "));
        assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkValidPasswordFieldWithoutEnoughChars(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Pw90"));
        assertEquals(exception.getMessage(),"Password Length");
    }

    @Test public void checkEmptyPasswordFieldWithFewSpaceChars(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("   "));
        assertEquals(exception.getMessage(),"Password Length");
    }

    @Test public void checkValidPasswordFieldContainingFiveCases(){
        Exception exception = assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Pass9"));
        assertEquals(exception.getMessage(),"Password Length");
    }


    @Test
    public void checkInvalidPasswordFieldContainingOnlySpecialChars() {
        Exception exception = assertThrows(Exception.class, ()-> assertTrue(registrationController.checkPasswordField("?^$%£&\\")));
        assertEquals(exception.getMessage(),"Weak Password");
    }


}