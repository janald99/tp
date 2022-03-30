package seedu.address.model.buyer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.property.House;
import seedu.address.model.property.HouseType;
import seedu.address.model.property.PriceRange;
import seedu.address.model.property.PropertyToBuy;
import seedu.address.testutil.BuyerBuilder;

public class BuyerHouseTypeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BuyerHouseTypeContainsKeywordsPredicate firstPredicate = new BuyerHouseTypeContainsKeywordsPredicate(
                firstPredicateKeywordList);
        BuyerHouseTypeContainsKeywordsPredicate secondPredicate = new BuyerHouseTypeContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BuyerHouseTypeContainsKeywordsPredicate firstPredicateCopy = new BuyerHouseTypeContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_houseTypeContainsKeywords_returnsTrue() {
        // One keyword
        BuyerHouseTypeContainsKeywordsPredicate predicate = new BuyerHouseTypeContainsKeywordsPredicate(
                Collections.singletonList("Bungalow"));
        assertTrue(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));

        // Multiple keywords
        predicate = new BuyerHouseTypeContainsKeywordsPredicate(Arrays.asList("Bunga", "Low"));
        assertTrue(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));

        // Only one matching keyword
        predicate = new BuyerHouseTypeContainsKeywordsPredicate(Arrays.asList("Bunga", "High"));
        assertTrue(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));

        // Mixed-case keywords
        predicate = new BuyerHouseTypeContainsKeywordsPredicate(Arrays.asList("BuNg", "aLoW"));
        assertTrue(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));
    }

    @Test
    public void test_houseTypeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BuyerHouseTypeContainsKeywordsPredicate predicate = new BuyerHouseTypeContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));

        // Non-matching keyword
        predicate = new BuyerHouseTypeContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BuyerBuilder().withProperty(new PropertyToBuy(
                new House(HouseType.BUNGALOW, "Kranji"),
                new PriceRange(100, 500))).build()));

        // Keywords match phone, email and address, but does not match House Type
        predicate = new BuyerHouseTypeContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new BuyerBuilder().withName("Alice").withPhone("12345")
                .withProperty(new PropertyToBuy(new House(HouseType.BUNGALOW, "Kranji"),
                        new PriceRange(100, 500))).build()));
    }
}
