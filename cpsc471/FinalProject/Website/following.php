<html>
<head>
<link rel='stylesheet' type='text/css' href='tablestyle.css'>
</head>
<body text='white' style='background-color:#48755b;'>
<h1>Following</h1>
<a href='index.php'>Home </a>
<?php
session_start();
// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");
// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

    $following = mysqli_query($con,"SELECT Email_address2 FROM FOLLOW WHERE Email_address1 = ". $_SESSION['email']);
    echo "<table border='22'>
        <tr>
        <th>Following</th>
        </tr>";
    while($followingarray = mysqli_fetch_array($following)) {
        $followingName = $followingarray['Name'];
        $followingID = $followingarray['ID'];
        echo $followingName." ".$followingID;
        // fix the query above and turn these into table entries of links leading to another page which queries all posts
    }
?>

</body>
</html>