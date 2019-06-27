<?php session_start();

function getRealIpAddr()
{
    if (!empty($_SERVER['HTTP_CLIENT_IP']))   //check ip from share internet
    {
      $ip=$_SERVER['HTTP_CLIENT_IP'];
    }
    elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR']))   //to check ip is pass from proxy
    {
      $ip=$_SERVER['HTTP_X_FORWARDED_FOR'];
    }
    else
    {
      $ip=$_SERVER['REMOTE_ADDR'];
    }
    return $ip;
}

echo "<html>
    <head>
    <link rel='stylesheet' type='text/css' href='tablestyle.css'>
    </head>
    <body text='white' style='background-color:#48755b;'>
    <h1>mysocial</h1>";
    
    
    if(isset($_SESSION['email'])) {
      echo "<a href='create_thread.php'>CreateThread</a>";
    }

echo "<html>
      <form action = 'search_thread.php' method = 'post'>
      <input type = 'text' name = 'search' placeholder = 'Search For Threads'>
      <input type = 'submit' name = 'submit search'> </button>
      </form>";

if(isset($_GET['command']) && $_GET['command'] == 'logout'){
  $_SESSION = array();
  if(ini_get("session.use_cookies")) {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000,
      $params["path"], $params["domain"],
      $params["secure"], $params["httponly"]
    );
  }
  session_destroy();
  session_start();
  header('Location:index.php');
}
if(!isset($_SESSION['email'])) {
  echo "<a href='index.php'>Home</a>
        <a href='sign-up.php'>Sign-up </a>
        <a href='login_page.php'>Log-in</a>";
}
if(isset($_SESSION['email'])) {
  echo "<a href='following.php'>Following</a>
        <a href='index.php?command=logout&'>Log out</a>";
}

// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");

// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  if(isset($_GET['followemail']) && isset($_SESSION['email'])) {
    mysqli_query($con,"INSERT INTO FOLLOW (Email_address1, Email_address2) VALUES('". $_SESSION['email'] ."','". $_GET['followemail'] ."')");
    $following = mysqli_query($con,"SELECT LOGGED_IN.Name, LOGGED_IN.ID FROM LOGGED_IN WHERE LOGGED_IN.Email_address = '".$_GET['followemail']."'");
    $followingarray = mysqli_fetch_array($following);
    $followingName = $followingarray['Name'];
    $followingID = $followingarray['ID'];
    echo "<br>Now following ".$followingName." #".$followingID;
  }

  $threads=mysqli_query($con,"SELECT * FROM THREAD"); // get threads here
  echo "<form action='index.php' method='get'>
          <br><h3> Create a post</h3>
          Caption: <input type='text' name='caption' placeholder = 'Optional'><br>
          Link: <input type='text' name='link' placeholder = 'Required'><br>
          Thread: <select name='thread' id='threadselection'>
                    <option value='' selected disabled hidden>Optional</option>";
            while($thread = mysqli_fetch_array($threads)) {
              echo "<option value='".$thread['Name']."'>".$thread['Name']."</option>";
            }
  echo           "</select>
          <input type='submit' value='Submit'>
        </form>";
  
  if(isset($_GET['caption']) && isset($_GET['link']))
  {
    $my_link = $_GET['link'];
    if($my_link == "") {
      echo "Please submit a link!";
    }
    else {
      if(isset($_SESSION['email'])) {
        mysqli_query($con,"INSERT INTO POST (Link, Caption, Email_address) VALUES('". $my_link."','". $_GET['caption'] ."','". $_SESSION['email'] ."')");
      } else {
        mysqli_query($con,"INSERT INTO POST (Link, Caption) VALUES('". $my_link."','". $_GET['caption'] ."')");
      }
      if(isset($_GET['thread'])) {
        $_submitted_thread = $_GET['thread'];
        echo "Thread: '". $_submitted_thread ."' selected!<br>";
        mysqli_query($con,"INSERT INTO CONTAINS (Name, Link) VALUES('". $_submitted_thread ."','". $my_link ."')");
      }
      echo "Link: '<a href='".$my_link."'>". $my_link ."</a>' submitted!";
    }
  }

if(!isset($_SESSION['email'])) { // get posts here
  $posts=mysqli_query($con,"SELECT * FROM POST");
  $id = rand(100 , 999);
  $_SESSION['id'] = $id;
  $_SESSION['name'] = "anon";
  // echo "I love you Eena";
} else {
  echo "<br>". "<h3>Welcome, ". $_SESSION['name']."<h3/>";
  $email = $_SESSION['email'];
  $posts=mysqli_query($con,"SELECT SUBSCRIBES_TO.Name, POST.Caption, CONTAINS.Link 
                            FROM POST, SUBSCRIBES_TO, THREAD, CONTAINS 
                            WHERE SUBSCRIBES_TO.Email_address = '$email' AND SUBSCRIBES_TO.Name = CONTAINS.Name AND SUBSCRIBES_TO.NAME = THREAD.NAME AND CONTAINS.Link = POST.Link");
  $posts=mysqli_query($con,"SELECT * FROM POST");
  //echo $_SESSION['id']. "<br>";//. $_SESSION['password']. "<br>";
}
if(isset($_GET['thread_name'])) {
  echo "<br><h2>". $_GET['thread_name']. "</h2><br>";
  $threadName = $_GET['thread_name'];
  $posts=mysqli_query($con,"SELECT P.* FROM POST AS P, CONTAINS WHERE P.Link = CONTAINS.Link AND CONTAINS.Name = '".$threadName."'");
}

if(isset($_GET['votelink'])) {
  // create anonymous user
  if(!isset($_SESSION['email'])) {
    mysqli_query($con,"INSERT INTO USER (Name, ID, IP_ADDRESS) VALUES ('". $_SESSION['name']."','". $_SESSION['id'] ."','". getRealIpAddr() ."')");
  }
  mysqli_query($con,"INSERT INTO VOTE (Link, Name, ID) VALUES('". $_GET['votelink']."','". $_SESSION['name'] ."','". $_SESSION['id'] ."')");
}

echo "<table border='22'>
<tr>
<th>Votes</th>
<th>Link</th>
<th>Caption</th>
<th>Thread(s)</th>
<th>Poster (click to follow)</th>
<th>Comments</th>
</tr>";

while($row = mysqli_fetch_array($posts))
 {
$currentPostsLink = $row['Link'];
$currentPostsThread = mysqli_query($con,"SELECT CONTAINS.Name FROM CONTAINS WHERE CONTAINS.Link = '".$currentPostsLink."'");
$currentPostsVotes = mysqli_query($con,"SELECT COUNT(*) AS NUMVOTES FROM VOTE WHERE VOTE.Link = '".$currentPostsLink."'");
 echo "<tr>";
 echo "<td><a href='index.php?votelink=".$currentPostsLink."'>".mysqli_fetch_array($currentPostsVotes)['NUMVOTES']."</a></td>";
 echo "<td>" . "<a href=" . $currentPostsLink . " target='_blank'>view</a>" . "</td>";
 echo "<td>" . $row['Caption'] . "</td>";

    echo "<td>";
    while($threadrow = mysqli_fetch_array($currentPostsThread)) {
      echo $threadrow['Name'].",";
    }  
    echo "</td>";
 if($row['Email_address'] != NULL) {
    $currentPoster = mysqli_query($con,"SELECT LOGGED_IN.Name, LOGGED_IN.ID FROM LOGGED_IN WHERE LOGGED_IN.Email_address = '".$row['Email_address']."'");
    $poster = mysqli_fetch_array($currentPoster);
    $posterName = $poster['Name'];
    $posterID = $poster['ID'];
    echo "<td><a href='index.php?followemail=".$row['Email_address']."'>"." ".$posterName." #".$posterID."</td>";
    // echo "<td>".$row['Email_address']."</td>";
 } else {
    echo "<td> </td>";
 }
 echo "<td>" . "<a href=comment.php?link=".$currentPostsLink." target='_blank'>comments</a>" . "</td>";
 echo "</tr>";
 }
echo "</table>";


mysqli_close($con);
?>

</body>
</html>