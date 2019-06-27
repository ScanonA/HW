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

$currentlink = $_GET["link"];

echo "<html>
<head>
<link rel='stylesheet' type='text/css' href='tablestyle.css'>
</head>
<body text='white' style='background-color:#48755b;'>
<h3>Showing comments for <a href='".$currentlink."'>". $currentlink ."</a></h3>
<a href='index.php'>Home </a>";

if(isset($_GET['command']) && $_GET['command'] == 'logout'){
  session_unset();
}
if(!isset($_SESSION['email'])) {
  echo "<a href='sign-up.php'>Sign-up </a>
        <a href='login_page.php'>Log-in</a>";
}
if(isset($_SESSION['email'])) {
  echo "<a href='index.php?command=logout&'>Log out</a>";
}

// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");

// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
?>

<form action="comment.php?link=".$currentlink method="get">
   <input type='hidden' name='link' value='<?php echo "$currentlink";?>'/>
   Leave a comment: <input type="text" name="comment"><br>
   <input type="submit" value="Submit">
</form>

<?php

if(isset($_GET['comment']))
{
  $my_comment = $_GET["comment"];

  // create anonymous user
  if(!isset($_SESSION['email'])) {
    mysqli_query($con,"INSERT INTO USER (Name, ID, IP_ADDRESS) VALUES ('". $_SESSION['name']."','". $_SESSION['id'] ."','". getRealIpAddr() ."')");
  }

  mysqli_query($con,"INSERT INTO COMMENT (CText, Name, ID, Link) VALUES('". $my_comment."','". $_SESSION['name'] ."','". $_SESSION['id'] ."','". $currentlink ."')");
   echo "Comment: '". $my_comment ."' submitted!";
}

$comments=mysqli_query($con,"SELECT * FROM `comment` WHERE `Link` = '".$currentlink."'");
echo "<table border='22'>
<tr>
<th>Comment</th>
<th>Name</th>
<th>ID</th>
</tr>";
// <th>Email_address</th>

while($row = mysqli_fetch_array($comments))
 {
 echo "<tr>";
 echo "<td>" . $row['CText'] . "</td>";
 echo "<td>" . $row['Name'] . "</td>";
 echo "<td>" . $row['ID'] . "</td>";
 echo "</tr>";
 }
echo "</table>";

mysqli_close($con);
?>

</body>
</html>