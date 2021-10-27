package com.example.ingsw.controllers;

import com.example.ingsw.InsertFilmReviewScreen;
import com.example.ingsw.utility.TitleException;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class InsertFilmReviewScreenTest {

    final InsertFilmReviewScreen screen = new InsertFilmReviewScreen("5353");

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
    public void emptyTitleAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("", 0.0f));
    }

    @Test
    public void shortTitleAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("Lo adoro",0.0f));
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.5f));
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.5f));
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 0.5f));
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 5.0f));
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("0", 0.5f));
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview("0", 5.0f));
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 0.5f));
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 5.0f));
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 0.5f));
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 5.0f));
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("0.0", 0.5f));
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview("0.0", 5.0f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("-0.0", 0.5f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMax() throws Exception {
        assertFalse(screen.isValidReview("-0.0", 5.0f));
    }

    @Test
    public void TitleIsANumberAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())), 0.0f));
    }

    @Test
    public void TitleIsANegativeNumberAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs((int)randomNumber())*-1), 0.0f));
    }

    @Test
    public void TitleIsZeroAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("0", 0.0f));
    }

    @Test
    public void TitleIsPositiveFloatAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())), 0.0f));
    }

    @Test
    public void TitleIsNegativeFloatAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview(String.valueOf(Math.abs(randomNumber())*-1), 0.0f));
    }


    @Test
    public void TitleIsPositiveZeroFloatAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("0.0", 0.0f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndInvalidValutationAtMin() throws Exception {
        assertFalse(screen.isValidReview("-0.0", 0.0f));
    }

}
