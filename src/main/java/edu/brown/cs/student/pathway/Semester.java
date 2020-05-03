package edu.brown.cs.student.pathway;

import java.util.List;
import java.util.Set;

/**
 * The Semester Class contains the courses a student takes in a specific
 * semester (made in the makePathway method). Additional courses info
 * is in the Semester class that allows Pathway customization in the GUI.
 */
public class Semester {
  private List<Node> courses;
  private int semnumber;
  private String courseid1;
  private String courseid2;
  private String courseid3;
  private String courseid4;
  private String coursename1;
  private String coursename2;
  private String coursename3;
  private String coursename4;
  private String prof1;
  private String prof2;
  private String prof3;
  private String prof4;
  private String url1;
  private String url2;
  private String url3;
  private String url4;
  private int sems1;
  private int sems2;
  private int sems3;
  private int sems4;
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
    courses = taking; // courses generated by algo, before edits
    this.setCourses();
    numcourses = 0;
    maxhrs = 0.0;
    avghrs = 0.0;
    rating = 0.0;
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
   * Sets courses.
   */
  public void setCourses() {
    for (int i = 0; i < courses.size(); i++) {
      switch (i) {
        case 0:
          Node n = courses.get(0);
          courseid1 = n.getId();
          coursename1 = n.getName();
          prof1 = n.getProfessor();
          url1 = n.getSsurl();
          Set<Integer> semSet = n.getSemestersOffered();
          if (semSet.size() == 2) {
            sems1 = 2;
          } else { // only 1 number in the set
            for (Integer integer : semSet) {
              sems1 = integer;
            }
          }
          break;
        case 1:
          n = courses.get(1);
          courseid2 = n.getId();
          coursename2 = n.getName();
          prof2 = n.getProfessor();
          url2 = n.getSsurl();
          semSet = n.getSemestersOffered();
          if (semSet.size() == 2) {
            sems2 = 2;
          } else { // only 1 number in the set
            for (Integer integer : semSet) {
              sems2 = integer;
            }
          }
          break;
        case 2:
          n = courses.get(2);
          courseid3 = n.getId();
          coursename3 = n.getName();
          prof3 = n.getProfessor();
          url3 = n.getSsurl();
          semSet = n.getSemestersOffered();
          if (semSet.size() == 2) {
            sems3 = 2;
          } else { // only 1 number in the set
            for (Integer integer : semSet) {
              sems3 = integer;
            }
          }
          break;
        case 3:
          n = courses.get(3);
          courseid4 = n.getId();
          coursename4 = n.getName();
          prof4 = n.getProfessor();
          url4 = n.getSsurl();
          semSet = n.getSemestersOffered();
          if (semSet.size() == 2) {
            sems4 = 2;
          } else { // only 1 number in the set
            for (Integer integer : semSet) {
              sems4 = integer;
            }
          }
          break;
        default:
          break;
      }
    }
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
   * Gets courseid 1.
   *
   * @return the courseid 1
   */
  public String getCourseid1() {
    return courseid1;
  }

  /**
   * Gets courseid 2.
   *
   * @return the courseid 2
   */
  public String getCourseid2() {
    return courseid2;
  }

  /**
   * Gets courseid 3.
   *
   * @return the courseid 3
   */
  public String getCourseid3() {
    return courseid3;
  }

  /**
   * Gets courseid 4.
   *
   * @return the courseid 4
   */
  public String getCourseid4() {
    return courseid4;
  }

  /**
   * Gets coursename 1.
   *
   * @return the coursename 1
   */
  public String getCoursename1() {
    return coursename1;
  }

  /**
   * Gets coursename 2.
   *
   * @return the coursename 2
   */
  public String getCoursename2() {
    return coursename2;
  }

  /**
   * Gets coursename 3.
   *
   * @return the coursename 3
   */
  public String getCoursename3() {
    return coursename3;
  }

  /**
   * Gets coursename 4.
   *
   * @return the coursename 4
   */
  public String getCoursename4() {
    return coursename4;
  }

  /**
   * Gets prof 1.
   *
   * @return the prof 1
   */
  public String getProf1() {
    return prof1;
  }

  /**
   * Gets prof 2.
   *
   * @return the prof 2
   */
  public String getProf2() {
    return prof2;
  }

  /**
   * Gets prof 3.
   *
   * @return the prof 3
   */
  public String getProf3() {
    return prof3;
  }

  /**
   * Gets prof 4.
   *
   * @return the prof 4
   */
  public String getProf4() {
    return prof4;
  }

  /**
   * Get url1.
   * @return url
   */
  public String getUrl1() {
    return url1;
  }

  /**
   * Get url2.
   * @return url
   */
  public String getUrl2() {
    return url2;
  }

  /**
   * Get url3.
   * @return url
   */
  public String getUrl3() {
    return url3;
  }

  /**
   * Get url4.
   * @return url
   */
  public String getUrl4() {
    return url4;
  }

  /**
   * Gets sems 1.
   *
   * @return the sems 1
   */
  public int getSems1() {
    return sems1;
  }

  /**
   * Gets sems 2.
   *
   * @return the sems 2
   */
  public int getSems2() {
    return sems2;
  }

  /**
   * Gets sems 3.
   *
   * @return the sems 3
   */
  public int getSems3() {
    return sems3;
  }

  /**
   * Gets sems 4.
   *
   * @return the sems 4
   */
  public int getSems4() {
    return sems4;
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
