<?php session_start();?>


<?php

// http://itman.in/en/how-to-get-client-ip-address-in-php/
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

$email = $_POST["email"];
$password = $_POST["password"];
$name = $_POST["name"];
$id = $_SESSION['id'];
$ipaddr = getRealIpAddr();
//echo $email. "<br>". $password. "<br>". $name. "<br>". $ipaddr. "<br>";


// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");

// Check connection
if (mysqli_connect_errno($con))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
  $sql = "INSERT INTO USER (Name, ID, IP_ADDRESS) VALUES ('". $name."','". $id ."','". $ipaddr ."')";
  $sql1 = "INSERT INTO LOGGED_IN (Email_address, password, Name, ID, IP_ADDRESS) VALUES ('".$email."','".$password."','".$name."', '" .$id. "','". $ipaddr ."')";
  
 if (!mysqli_query($con,$sql))
  {
  die('Error: ' . mysqli_error($con));
  }
  if(!mysqli_query($con,$sql1))
  {
  die('Error' . mysqli_error($con));
  }
$_SESSION['email'] = $email;
$_SESSION['password'] = $password;
$_SESSION['name'] = $name;
echo "1 record added";//. $_SESSION['email']. "<br>". $_SESSION['password']. "<br>";
header('Location:index.php');


mysqli_close($con);
?>

