package com.example.INGSW;


import org.junit.Assert;
import org.junit.Test;

public class InsertReviewScreenTest {

    InsertReviewScreen screen = new InsertReviewScreen("5353");

    @Test
    public void validTitleAndValidValutationAtMin() {
        Assert.assertTrue(screen.isValidReview("Bello", 0.5f));
    }

    @Test
    public void validTitleAndValidValutationAtMax() {
        Assert.assertTrue(screen.isValidReview("Bello", 5.0f));
    }

    @Test
    public void emptyTitleAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("", 0.5f));
    }

    @Test
    public void emptyTitleAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("", 5.0f));
    }

    @Test
    public void tooLongTitleAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("Mamma mia sto film è troppo bello", 0.5f));
    }

    @Test
    public void tooLongTitleAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("Mamma mia sto film è troppo bello", 0.5f));
    }

    @Test
    public void validTitleAndInvalidValutationAtMin() throws NumberFormatException {
        Assert.assertFalse(screen.isValidReview("Fantastico", 0.0f));
    }

    @Test
    public void emptyTitleAndInvalidValutationAtMin() throws NumberFormatException {
        Assert.assertFalse(screen.isValidReview("", 0.0f));
    }

    @Test
    public void shortTitleAndInvalidValutationAtMin() throws NumberFormatException {
        Assert.assertFalse(screen.isValidReview("", 0.0f));
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMin() throws NumberFormatException {
        Assert.assertFalse(screen.isValidReview("55", 0.5f));
    }

    @Test
    public void TitleIsANumberAndGoodValutationAtMax() throws NumberFormatException {
        Assert.assertFalse(screen.isValidReview("5107", 5.0f));
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-5107", 0.5f));
    }

    @Test
    public void TitleIsANegativeNumberAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("-67", 5.0f));
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("0", 0.5f));
    }

    @Test
    public void TitleIsZeroAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("0", 5.0f));
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("6.2", 0.5f));
    }

    @Test
    public void TitleIsPositiveFloatAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("6.2", 5.0f));
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-8.3", 0.5f));
    }

    @Test
    public void TitleIsNegativeFloatAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("-8.3", 5.0f));
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("0.0", 0.5f));
    }

    @Test
    public void TitleIsPositiveZeroFloatAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("0.0", 5.0f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-0.0", 0.5f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndGoodValutationAtMax() {
        Assert.assertFalse(screen.isValidReview("-0.0", 5.0f));
    }


    @Test
    public void TitleIsANumberAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("55", 0.0f));
    }

    @Test
    public void TitleIsANegativeNumberAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-5107", 0.0f));
    }

    @Test
    public void TitleIsZeroAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("0", 0.0f));
    }

    @Test
    public void TitleIsPositiveFloatAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("6.2", 0.0f));
    }

    @Test
    public void TitleIsNegativeFloatAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-8.3", 0.0f));
    }


    @Test
    public void TitleIsPositiveZeroFloatAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("0.0", 0.0f));
    }

    @Test
    public void TitleIsNegativeZeroFloatAndInvalidValutationAtMin() {
        Assert.assertFalse(screen.isValidReview("-0.0", 0.0f));
    }

}