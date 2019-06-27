<?php 
session_start();


// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");

// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$thread_name = $_POST["search"];
$sql = "SELECT Name FROM THREAD WHERE '$thread_name' = Name";
$thread = mysqli_query($con, $sql);

if (!$thread) {
    die('Error: ' . mysqli_error($con));
}
while ($thread_result = mysqli_fetch_assoc($thread)) {
    $name = $thread_result['Name'];
}

echo "<html>
    <head>
    <link rel='stylesheet' type='text/css' href='tablestyle.css'>
    </head>
    <body text='white' style='background-color:#48755b;'>
    <h1>mysocial</h1>";


if(!isset($name)) {
    $name = "No thread matched your search";
    echo $name. "<a href='index.php?thread_name=".$name."'>Home</a>";

} else {
    // $_SESSION ['thread_name'] = $name; 
    echo $name;
    header('Location:index.php?thread_name='.$name);
}
?>

</body>
</html>