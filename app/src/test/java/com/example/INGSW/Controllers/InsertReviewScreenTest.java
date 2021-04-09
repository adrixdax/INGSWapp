package com.example.INGSW.Controllers;

import com.example.INGSW.InsertReviewScreen;
import com.example.INGSW.Utility.TitleException;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class InsertReviewScreenTest {

    InsertReviewScreen screen = new InsertReviewScreen("5353");

    public float randomNumber(){
        return new Random().nextFloat();
    }

    @Test
    public void validTitleAndValidValutationAtMin() throws Exception {
        Assert.assertTrue(screen.isValidReview("Bello", 0.5f));
    }

    @Test
    public void validTitleAndValidValutationAtMax() throws Exception {
        Assert.assertTrue(screen.isValidReview("Bello", 5.0f));
    }

    @Test
    public void emptyTitleAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("", 0.5f));
        assertEquals(TitleException.TOO_SHORT.toString(),exception.getMessage());
    }

    @Test
    public void emptyTitleAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("", 5.0f));
        assertEquals(TitleException.TOO_SHORT.toString(),exception.getMessage());
    }

    @Test
    public void tooLongTitleAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("Mamma mia sto film è troppo bello", 0.5f));
        assertEquals(TitleException.TOO_LONG.toString(),exception.getMessage());
    }

    @Test
    public void tooLongTitleAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("Mamma mia sto film è troppo bello", 5.0f));
        assertEquals(TitleException.TOO_LONG.toString(),exception.getMessage());
    }

    @Test
    public void validTitleAndInvalidValutationAtMin() throws Exception {
        Assert.assertFalse(screen.isValidReview("Fantastico", 0.0f));
    }

    @Test
    public void emptyTitleAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("", 0.0f));
        assertEquals(TitleException.TOO_SHORT.toString(),exception.getMessage());
    }

    @Test
    public void shortTitleAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("Lo adoro",0.0f));
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMin(){
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMax() throws NumberFormatException {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 5.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0", 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0", 5.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> {
            screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 5.0f);
        });
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 5.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0.0", 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0.0", 5.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("-0.0", 0.5f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMax() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("-0.0", 5.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsANumberAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsANegativeNumberAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsZeroAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0", 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsPositiveFloatAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeFloatAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }


    @Test
    public void TitleIsPositiveZeroFloatAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("0.0", 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

    @Test
    public void TitleIsNegativeZeroFloatAndInvalidValutationAtMin() {
        Exception exception = assertThrows(Exception.class, ()-> screen.isValidReview("-0.0", 0.0f));
        assertEquals(TitleException.IS_NUMBER.toString(),exception.getMessage());
    }

}
