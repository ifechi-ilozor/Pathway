package edu.brown.cs.student.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.student.pathway.Node;
import edu.brown.cs.student.pathway.Semester;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private static final int HIGH = 40;
  private static final int LOW = 30;
  private static PathwayProgram pathwayProgram;
  private static List<Semester> pathway1; //why is this static if they can alter them in gui
  private static List<Semester> pathway2;
  private static List<Semester> pathway3;
  private Database db;
  private static List<String> courseList;
  private static List<String> concentrationList;
  private static String username;


  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) throws SQLException {
    new Main(args).run();
  }

  private String[] args;


  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Runs application on port. Launches gui if indicated. Starts the REPL.
   */
  private void run() throws SQLException {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    runSparkServer((int) options.valueOf("port"));

    db = new Database("data/coursesDB.db");
    courseList = db.getAllCourseIDs();
    concentrationList = db.getConcentrations();

    pathwayProgram = new PathwayProgram();

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Sets up the Spark server, with the 5 unique pages we have right now.
   * @param port Default port is 4567.
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    // Setup Spark Routes
    Spark.get("/login", new LoginHandler(), freeMarker);
    Spark.post("/generate", new GenerateHandler(), freeMarker);
    Spark.get("/faqs", new FaqHandler(), freeMarker);
    Spark.get("/signup", new SignUpHandler(), freeMarker);
    Spark.post("/mypath", new PathLandingHandler(), freeMarker);
    Spark.get("/mypath/:id", new PathwayHandler(), freeMarker);

  }

  /**
   * LoginHandler handles the main landing page, logging in at /login. On the main login
   * page, the user has the option to log in as a user which gives them their already chosen pathway.
   * They can also choose to create an account or login is a guest, which will directly take them to
   * the generate page.
   */
  private static class LoginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Pathway");
      return new ModelAndView(variables, "main.ftl");
    }

  }

  /**
   * GenerateHandler handles the /generate page. The generate page is where the user puts in the
   * necessary information in order to run the Pathway program. The ftl file uses two variables from the
   * database, the concentrationList and the courseList. These two variables are used for the dropdown menus
   * so the user can select a concentration and courses they've received credit for.
   */
  private static class GenerateHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      Map<String, Object> variables = ImmutableMap
          .of("title", "Pathway", "results", "", "concentrationList", concentrationList,
              "courseList", courseList);
      return new ModelAndView(variables, "generate.ftl");
    }

  }

  /**
   * PathLandingHandler handles the landing page for the display of the user's three pathways. 
   */
  private static class PathLandingHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      QueryParamsMap qm = req.queryMap();
      String concentration;
      String display = null;


      if (qm.value("semester") == null) {
        if (pathwayProgram.getPath1()!=null) { //going from each path page to all the paths so dont
          // remake
          // them just show the last pathways made
          concentration = pathwayProgram.getConcentrationName();
          if (concentration == null) {
            concentration = "Computer Science B.A";
          }
          display = "Pathways generated for the concentration: " + concentration;
          pathway1 = pathwayProgram.getPath1();
          pathway2 = pathwayProgram.getPath2();
          pathway3 = pathwayProgram.getPath3();
        } else { //use default aka user signing in and should see premade pathways
          concentration = "Computer Science B.A";
          pathwayProgram.setConcentration("computerscienceba");
          pathwayProgram.makePathways("computerscienceba", new HashSet<>(), 1,false);
          display = "Pathways generated for the concentration: " + concentration;
          pathway1 = pathwayProgram.getPath1();
          pathway2 = pathwayProgram.getPath2();
          pathway3 = pathwayProgram.getPath3();
        }
      } else { //new user or guest user
        concentration = qm.value("concentration");
        if (concentration == null) {
          concentration = "Computer Science B.A";
        }
        String concentrationId = pathwayProgram.getConcentrationMap()
            .get(qm.value("concentration"));
        pathwayProgram.setConcentrationName(concentration);
        pathwayProgram.setConcentration(concentrationId);
        int semesterLevel = Integer.parseInt(qm.value("semester"));
        boolean aggressive = false;
        if (qm.value("aggressive") != null) {
          aggressive = true;
        }
        String coursestaken = qm.value("results");
        String[] cList = coursestaken.split(",");
        Set<Node> taken = new HashSet<>();
        for (int i = 0; i < cList.length; i++) {
          for (Node c: pathwayProgram.getCourseSet()) {
            if (c.getId().equals(cList[i])) {
              taken.add(c);
            }
          }
        }

        pathwayProgram.makePathways(concentrationId, taken, semesterLevel, aggressive);
        display = "Pathways generated for the concentration: " + concentration;
        pathway1 = pathwayProgram.getPath1();
        pathway2 = pathwayProgram.getPath2();
        pathway3 = pathwayProgram.getPath3();
      }



      List<Object> titles = new ArrayList<>();
      titles.add("Pathway");

      Map<String, Object> variables = ImmutableMap
          .of("header", display, "results1", pathway1, "results2", pathway2, "results3",
              pathway3, "stats", pathwayProgram);
      return new ModelAndView(variables, "pathway.ftl");
    }
  }

  private static class PathwayHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String pathNum = req.params(":id");
      Integer num = Integer.parseInt(pathNum);
      List<Semester> path;
      switch (num) {
        case 1:
          path = pathway1;
          break;
        case 2:
          path = pathway2;
          break;
        case 3:
          path = pathway3;
          break;
        default:
          path = pathway1;
          break;
      }
      List<String> pathnumlst = new ArrayList<>();
      pathnumlst.add(pathNum);
      Map<String, List<? extends Object>> variables =
          ImmutableMap.of("id", pathnumlst, "results", path, "courseList", courseList);
      return new ModelAndView(variables, "mypath.ftl");

    }
  }

  private static class SignUpHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws SQLException {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway Sign Up", "results", "");
      return new ModelAndView(variables, "signup.ftl");
    }
  }

  private static class FaqHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Pathway FAQs", "results", "");
      return new ModelAndView(variables, "faqs.ftl");
    }
  }

  /**
   * Displays an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}
