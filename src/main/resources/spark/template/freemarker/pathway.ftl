<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <h1>Melissa's Pathways</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->

    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/pathway.css">
  </head>
  <body>


  <p> ${content} </p>

  <br>

  <a href="mypath/1" class="link" name="pathway" id="pathway" value="one">
    <div id="container">
      Pathway 1
    </div>
  </a>

  <a href="mypath/2" class="link">
    <div id="container">
      Pathway 2
    </div>
  </a>

  <a href="mypath/3" class="link">
    <div id="container">
      Pathway 3
    </div>
  </a>

  <br>

  <p> My plan is to have some kind of hover-summary on pathways <br>
    (e.g. maybe Pathway length in semesters, Avg workload per week, etc) </p>



  <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>


  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>