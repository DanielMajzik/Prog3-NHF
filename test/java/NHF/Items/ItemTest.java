package NHF.Items;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    Item item;

    @BeforeEach
    void setUp() {
        ExistingItems.items.add(new Item("Part1", null, null));
        ExistingItems.items.add(new Item("Part2", null, null));

        item = new Item("Item1", new String[]{"Part1", "Part2"}, new int[]{4, 3});
    }

    @AfterEach
    void tearDown() {
        item = null;
    }

    @Test
    void getName() {
        assertEquals("Item1", item.getName());
    }

    @Test
    void getPartsQuantityAsString() {
        assertEquals("4,3", item.getPartsQuantityAsString());
    }

    @Test
    void getPartsAsString() {
        assertEquals("Part1,Part2", item.getPartsAsString());
    }
}