package com.example.ingsw.controllers;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationControllerTest {

    final RegistrationController registrationController = new RegistrationController(null,"");

    /**TEST DI SUCCESSO**/


    @Test
    public void checkValidPasswordFieldStartingWithNumberEndingWithUppercase() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("90PassworD"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithNumberEndingWithLowercase() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("90PassworD"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithUppercaseEndingWithNumber() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("Password90"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithUppercaseEndingWithLowercase() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("Pass90word"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithLowercaseEndingWithNumber() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("passWORD90"));
    }

    @Test
    public void checkValidPasswordFieldStartingWithLowercaseEndingWithUppercase() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("pass90WORD"));
    }

    @Test
    public void checkValidPasswordFieldContainingSpecialChars() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("Password?^$40"));
    }

    @Test
    public void checkValidPasswordFieldContainingSixCases() throws Exception {
        Assert.assertTrue(registrationController.checkPasswordField("Pass98"));
    }


    /** TEST FALLIMENTARI **/



    @Test public void checkEmptyPasswordField(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField(""));
        Assert.assertEquals(exception.getMessage(),"Empty Password");
    }

    @Test public void checkEmptyPasswordFieldAndFullRePasswordField(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField(""));
        Assert.assertEquals(exception.getMessage(),"Empty Password");
    }

//
    @Test public void checkFullInvalidPasswordFieldWithoutLowercase(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("PASSWORD1"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutUppercase(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("password1"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutNumbers(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Password"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithoutChars(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("999999"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithOnlyUppercase(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("PASSWORD"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkFullInvalidPasswordFieldWithOnlyLowercase(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("password"));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }



    @Test public void checkEmptyPasswordFieldWithSpaceChars(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("      "));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }

    @Test public void checkValidPasswordFieldWithoutEnoughChars(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Pw90"));
        Assert.assertEquals(exception.getMessage(),"Password Length");
    }

    @Test public void checkEmptyPasswordFieldWithFewSpaceChars(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("   "));
        Assert.assertEquals(exception.getMessage(),"Password Length");
    }

    @Test public void checkValidPasswordFieldContainingFiveCases(){
        Exception exception = Assert.assertThrows(Exception.class, ()-> registrationController.checkPasswordField("Pass9"));
        Assert.assertEquals(exception.getMessage(),"Password Length");
    }


    @Test
    public void checkInvalidPasswordFieldContainingOnlySpecialChars() {
        Exception exception = Assert.assertThrows(Exception.class, ()-> Assert.assertTrue(registrationController.checkPasswordField("?^$%£&\\")));
        Assert.assertEquals(exception.getMessage(),"Weak Password");
    }


}