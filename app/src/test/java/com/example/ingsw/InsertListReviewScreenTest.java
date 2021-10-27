package com.example.ingsw;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class InsertListReviewScreenTest {

    final InsertListReviewScreen positiveScreen = new InsertListReviewScreen(true,String.valueOf(45328));
    final InsertListReviewScreen negativeScreen = new InsertListReviewScreen(false,String.valueOf(45328));
    final String positiveString="ha lasciato un \"mi piace\"";
    final String negativeString="ha lasciato un \"non mi piace\"";

    String generatedString= "";

    @Before
    public void initString() {
        generatedString = UUID.randomUUID().toString();
    }

    @Test
    public void emptyTextPositiveValue() {
        Assert.assertEquals(positiveString,positiveScreen.valutaLista("",positiveScreen.getPositive()));
    }

    @Test
    public void emptyTextNegativeValue() {
        Assert.assertEquals(negativeString,negativeScreen.valutaLista("",negativeScreen.getPositive()));
    }

    @Test
    public void notEmptyTextPositiveValue() {
        Assert.assertEquals(generatedString.replace("'","''"),positiveScreen.valutaLista(generatedString,positiveScreen.getPositive()));
    }

    @Test
    public void notEmptyTextNegativeValue() {
        Assert.assertEquals(generatedString.replace("'","''"),negativeScreen.valutaLista(generatedString,negativeScreen.getPositive()));
    }

}