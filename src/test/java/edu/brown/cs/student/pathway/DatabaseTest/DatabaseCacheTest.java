package edu.brown.cs.student.pathway.DatabaseTest;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.DatabaseCache;
import edu.brown.cs.student.main.DatabaseInterface;
import edu.brown.cs.student.pathway.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * DatabaseCacheTest.
 * @author nrshaida (Natalie Rshaidat)
 */
public class DatabaseCacheTest {
  DatabaseInterface cache;

  /**
   * Sets up the database connection.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Before
  public void setUp() throws SQLException {
    String file = "data/coursesDB.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
  }

  /**
   * Resets the the databases.
   * @author nrshaida (Natalie Rshaidat)
   */
  @After
  public void tearDown() {
    cache = null;
  }

  /**
   * Tests the getConcentrationCourses method returns the correct course data
   * the current db.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void validGetConcentrationData() throws SQLException {
    setUp();
    String validCon = "computationalbiologyba";
    Set<Node> comp = cache.getConcentrationCourses(validCon);
    assertEquals(23, comp.size());
    Map<String, Node> mapy = this.covertDict(comp);
    Node math10 = cache.getCourseData("MATH 0100");
    assertEquals(math10, mapy.get("MATH 0090").getNext());
    assertEquals(0, mapy.get("MATH 0090").getCategory());
    assertEquals(1, mapy.get("BIOL 0200").getCategory());
    assertNull(mapy.get("BIOL 0200").getNext());
    assertEquals(2, mapy.get("BIOL 0470").getCategory());
    assertNull(mapy.get("BIOL 0470").getNext());
    assertEquals(3, mapy.get("BIOL 0280").getCategory());
    assertNull(mapy.get("BIOL 0280").getNext());
    assertEquals(3, mapy.get("BIOL 0500").getCategory());
    assertNull(mapy.get("BIOL 0500").getNext());
    assertEquals(4, mapy.get("CHEM 0100").getCategory());
    Node chem33 = cache.getCourseData("CHEM 0330");
    assertEquals(chem33,mapy.get("CHEM 0100").getNext());
    assertEquals(4, mapy.get("CHEM 0350").getCategory());
    assertNull(mapy.get("CHEM 0350").getNext());
    Node cs16 = cache.getCourseData("CSCI 0160");
    assertEquals(cs16, mapy.get("CSCI 0150").getNext());
    assertEquals(5, mapy.get("CSCI 0150").getCategory());
    Node cs18 = cache.getCourseData("CSCI 0180");
    assertEquals(cs18, mapy.get("CSCI 0170").getNext());
    assertEquals(5, mapy.get("CSCI 0170").getCategory());
    assertNull(mapy.get("CSCI 0190").getNext());
    assertEquals(5, mapy.get("CSCI 0190").getCategory());
    assertNull(mapy.get("APMA 1650").getNext());
    assertEquals(6, mapy.get("APMA 1650").getCategory());
    assertNull(mapy.get("CSCI 1450").getNext());
    assertEquals(6, mapy.get("CSCI 1450").getCategory());
    assertNull(mapy.get("MATH 1610").getNext());
    assertEquals(6, mapy.get("MATH 1610").getCategory());
    assertNull(mapy.get("CSCI 1810").getNext());
    assertEquals(7, mapy.get("CSCI 1810").getCategory());
    assertNull(mapy.get("APMA 1080").getNext());
    assertEquals(7, mapy.get("APMA 1080").getCategory());
    Node ml = cache.getCourseData("CSCI 1420");
    assertEquals(ml,mapy.get("MATH 0520").getNext());
    assertEquals(8, mapy.get("MATH 0520").getCategory());
    assertNull(mapy.get("APMA 1690").getNext());
    assertEquals(8, mapy.get("APMA 1690").getCategory());
    assertNull(mapy.get("APMA 1660").getNext());
    assertEquals(8, mapy.get("APMA 1660").getCategory());
    tearDown();
  }

  /**
   * Tests invalid input on getters in the cache.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void invalidInput() throws SQLException {
    setUp();
    assertNull(cache.getCourseData(null));
    assertNull(cache.getConcentrationCourses(null));
    assertNull(cache.getRequirements(null));
    tearDown();
  }

  /**
   * Tests invalid input on getters in the cache.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void invaliddb() throws SQLException {
    String file = "data/coursesDBempty.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    assertTrue(cache.isEmptyCourses());
    tearDown();
  }

  /**
   * Tests invalid input on getters in the cache.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void invalidcol() throws SQLException {
    String file = "data/coursesDBColErr.db";
    Database realDB = new Database(file); // real database that handles sql queries
    cache = new DatabaseCache(realDB);
    assertFalse(cache.checkCoursesTable());
    tearDown();
  }

  /**
   * Tests valid input on getters in the cache.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void validdb() throws SQLException {
    setUp();
    String con = "cognitiveneuroscienceba";
    assertTrue(cache.hasConnection());
    assertTrue(cache.checkConcentration(con));
    tearDown();
  }

  /**
   * Tests getConcentrationsMap method in cache.
   * @author nrshaida (Natalie Rshaidat)
   */
  @Test
  public void validgetConcentrationsMap() throws SQLException {
    setUp();
    Map<String, String> mapy = cache.getConcentrationsMap();
    assertEquals(9, mapy.size());
    //gui names is key
    //table names is value
    assertTrue(mapy.containsKey("Economics B.A."));
    assertEquals("economicsba", mapy.get("Economics B.A."));
    assertTrue(mapy.containsKey("Economics (Business Track) B.A."));
    assertEquals("businesseconomicsba", mapy.get("Economics (Business Track) B.A."));
    assertTrue(mapy.containsKey("Cognitive Neuroscience B.A."));
    assertEquals("cognitiveneuroscienceba", mapy.get("Cognitive Neuroscience B.A."));
    assertTrue(mapy.containsKey("Cognitive Neuroscience B.S."));
    assertEquals("cognitiveneurosciencebs", mapy.get("Cognitive Neuroscience B.S."));
    assertTrue(mapy.containsKey("Computational Biology (Applied Mathematics and Statistics Track)" + " B.S."));
    assertEquals("computationalbiologyappliedmathematicsandstatisticstrackbs", mapy.get("Computational Biology (Applied Mathematics and Statistics Track) B.S."));
    assertTrue(mapy.containsKey("Computational Biology B.A."));
    assertEquals("computationalbiologyba", mapy.get("Computational Biology B.A."));
    assertTrue(mapy.containsKey("Computer Science B.A."));
    assertEquals("computerscienceba", mapy.get("Computer Science B.A."));
    assertTrue(mapy.containsKey("History of Art and Architecture B.A."));
    assertEquals("historyofartandarchitectureba", mapy.get("History of Art and Architecture B.A."));
    assertTrue(mapy.containsKey("Computational Biology (Computer Science Track) B.S."));
    assertEquals("computationalbiologycomputersciencetrackbs", mapy.get("Computational Biology " + "(Computer Science Track) B.S."));
    tearDown();
  }

  /**
   * CovertDict is a helper method that converts the set of nodes to a dictionary for
   * testing getConcentrationCourses.
   *
   * @param cp the cp
   * @return the map
   * @author nrshaida (Natalie Rshaidat)
   */
  public Map<String, Node> covertDict(Set<Node> cp) {
    Map<String, Node> mapy = new HashMap<>();
    for (Node n : cp) {
      mapy.put(n.getId(), n);
    }
    return mapy;
  }

}
