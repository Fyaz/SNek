import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import java.util.HashMap;
import game.Point;

class HashMapTest {

	@Test
	void test() {
		Map<Point, Integer> map = new HashMap<Point, Integer>();
		Point p = new Point(8,23);
		map.put(p, 1);
		assertTrue(map.containsKey(p));
		p = new Point(8,23);
		assertTrue(map.containsKey(p));
		p.setX(10);
		assertFalse(map.containsKey(p));
	}

}
