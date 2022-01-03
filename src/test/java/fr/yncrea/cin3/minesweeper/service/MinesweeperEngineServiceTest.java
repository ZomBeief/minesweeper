package fr.yncrea.cin3.minesweeper.service;

import fr.yncrea.cin3.minesweeper.domain.Minefield;
import fr.yncrea.cin3.minesweeper.exception.MinesweeperException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperEngineServiceTest {
    private MinesweeperEngineService service = new MinesweeperEngineService();

    @Test
    public void testAddMineOutOfBound() {
        Minefield minefield = new Minefield(4, 4);

        assertThrows(MinesweeperException.class, () -> {
            service.addMine(minefield, -1, 2);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.addMine(minefield, 2, -1);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.addMine(minefield, 4, 2);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.addMine(minefield, 2, 4);
        });
    }

    @Test
    public void testAddMine() {
        Minefield minefield = new Minefield(4, 4);

        // mines not present
        assertFalse(service.hasMine(minefield, 1, 2));
        assertFalse(service.hasMine(minefield, 1, 3));

        // adding 2 mines
        service.addMine(minefield, 1, 2);
        service.addMine(minefield, 1, 3);

        // mines should be present
        assertTrue(service.hasMine(minefield, 1, 2));
        assertTrue(service.hasMine(minefield, 1, 3));

        // mines not added
        assertFalse(service.hasMine(minefield, 2, 1));
        assertFalse(service.hasMine(minefield, 1, 1));
    }

    @Test
    public void testGetMineCountNearZero() {
        Minefield minefield = new Minefield(9, 9);

        for (long x = 0; x < 9; ++x) {
            for (long y = 0; y < 9; ++y) {
                assertEquals(0, service.getMineCountNear(minefield, x, y));
            }
        }
    }

    @Test
    public void testGetMineCountNearOne() {
        Minefield minefield = new Minefield(9, 9);
        service.addMine(minefield, 4, 4);

        assertEquals(1, service.getMineCountNear(minefield, 3, 3));
        assertEquals(1, service.getMineCountNear(minefield, 4, 3));
        assertEquals(1, service.getMineCountNear(minefield, 5, 3));

        assertEquals(1, service.getMineCountNear(minefield, 3, 4));
        assertEquals(1, service.getMineCountNear(minefield, 5, 4));

        assertEquals(1, service.getMineCountNear(minefield, 3, 5));
        assertEquals(1, service.getMineCountNear(minefield, 4, 5));
        assertEquals(1, service.getMineCountNear(minefield, 5, 5));

        assertEquals(0, service.getMineCountNear(minefield, 2, 2));
    }

    @Test
    public void testGetMineCountNearTwo() {
        Minefield minefield = new Minefield(9, 9);
        service.addMine(minefield, 4, 4);
        service.addMine(minefield, 6, 6);

        assertEquals(1, service.getMineCountNear(minefield, 5, 4));
        assertEquals(1, service.getMineCountNear(minefield, 4, 5));
        assertEquals(2, service.getMineCountNear(minefield, 5, 5));
    }

    @Test
    public void testGetMineCountNearEight() {
        Minefield minefield = new Minefield(9, 9);
        service.addMine(minefield, 3, 3);
        service.addMine(minefield, 4, 3);
        service.addMine(minefield, 5, 3);
        service.addMine(minefield, 3, 4);
        service.addMine(minefield, 5, 4);
        service.addMine(minefield, 3, 5);
        service.addMine(minefield, 4, 5);
        service.addMine(minefield, 5, 5);

        assertEquals(8, service.getMineCountNear(minefield, 4, 4));
    }

    @Test
    public void testCreateInvalidSize() {
        assertThrows(MinesweeperException.class, () -> {
            service.create(-2, -2, 0);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.create(-2, 2, 0);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.create(2, -2, 0);
        });
    }

    @Test
    public void testCreateInvalidMineNumber() {
        assertThrows(MinesweeperException.class, () -> {
            service.create(2, 2, -1);
        });

        assertThrows(MinesweeperException.class, () -> {
            service.create(2, 2, 5);
        });
    }

    @Test
    public void testCreate() {
        Minefield minefield = service.create(10, 20, 8);

        assertEquals(10, minefield.getWidth());
        assertEquals(20, minefield.getHeight());

        // count mines
        long count = 0;
        for (long x = 0; x < minefield.getWidth(); ++x) {
            for (long y = 0; y < minefield.getHeight(); ++y) {
                if (service.hasMine(minefield, x, y)) {
                    ++count;
                }
            }
        }

        assertEquals(8, count);
    }
}