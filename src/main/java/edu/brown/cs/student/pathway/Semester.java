package edu.brown.cs.student.pathway;

import java.util.List;

/**
 * The Semester Class contains the courses a student takes in a specific
 * semester (made in the makePathway method). Additional courses info
 * is in the Semester class that allows Pathway customization in the GUI.
 */
public class Semester {
  private List<Node> courses;
  private int semnumber;
  private double maxhrs;
  private double avghrs;
  private int numcourses;
  private double rating;

  /**
   * Instantiates a new Semester.
   *
   * @param sem    semester number (1, 2, ...)
   * @param taking courses that the student will take based on the results of the pathway
   */
  public Semester(int sem, List<Node> taking) {
    semnumber = sem;
    courses = taking; // courses generated by algo
  }

  /**
   * Sets the statistics for a semester like maxHrs, avghrs, and rating.
   */
  public void setStats() {
    for (Node c : this.courses) {
      maxhrs += c.getMaxHrs();
      avghrs += c.getAvgHrs();
      rating += c.getRating();
    }
  }

  /**
   * Resets the statistics for a semester to zero. Used in PathwayProgram.
   */
  public void resetStats() {
    maxhrs = 0.0;
    avghrs = 0.0;
    rating = 0.0;
  }

  // Getters for Apache Spark
  /**
   * Public getter method to return the maximum hours of a semester.
   *
   * @return a double representing maxhrs variable.
   */
  public double getMaxhrs() {
    return maxhrs;
  }

  /**
   * Getter method for the rating of a semester.
   *
   * @return a double variable, rating.
   */
  public double getRating() {
    return rating;
  }

  /**
   * Getter to return a double representing the average hours in a semester.
   *
   * @return double, avghrs variable.
   */
  public double getAvghrs() {
    return avghrs;
  }

  /**
   * Gets numcourses.
   *
   * @return the numcourses
   */
  public int getNumcourses() {
    numcourses = this.courses.size();
    return numcourses;
  }

  /**
   * getSemester gets the semester's integer representation.
   *
   * @return the semester as an integer
   */
  public int getSemnumber() {
    return semnumber;
  }

  /**
   * getCourses gets the courses that will be taken for this semester.
   *
   * @return the courses that will taken in this semester
   */
  public List<Node> getCourses() {
    return courses;
  }

}
