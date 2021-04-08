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

    RegistrationController registrationController = new RegistrationController(null,"");


    @Test (expected = Exception.class)
    public void checkEmptyPasswordField() throws Exception {
        registrationController.checkPasswordFields("","");
    }
}