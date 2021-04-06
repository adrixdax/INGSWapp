package com.example.INGSW.Controllers;

import android.text.Editable;
import android.widget.EditText;

import com.example.INGSW.R;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RegistrationControllerTest {

    RegistrationController registrationController;
    EditText mail,password,password2,nick;
    String propic = "default propic";


    @Before
    public void enableConstructor() {
        registrationController = new RegistrationController(null,propic);
    }

    @Test
    public void registerUser() {
       // mail.setText("test@gmail.com");
        password.setText("pw valida");
        password2.setText("pw valida");
        nick.setText("nick valido");

        registrationController.registerUser(mail,password,password2,nick,propic);
    }


}