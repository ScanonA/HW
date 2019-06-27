<?php session_start();?>

<?php
    $thread_name = $_POST["thread_name"];
    //$check;

    $con=mysqli_connect("localhost","root","471-my-root","mysocial");


//Check connection
    if (mysqli_connect_errno($con))
    {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    $email = $_SESSION['email'];
    $sql = "INSERT INTO THREAD (Name, Email_address) VALUES ('".$thread_name."', '".$email."')";

    if (!mysqli_query($con,$sql))
    {
        die('Error: ' . mysqli_error($con));
    }
        
    $sql = "SELECT * FROM THREAD WHERE '$thread_name' = Name";
    $thread = mysqli_query($con, $sql);
    if (!$thread) {
        die('Error: ' . mysqli_error($con));
        }
    while ($thread_result = mysqli_fetch_assoc($thread)) {
        $check = $thread_result['Name']. "<br>";
    }
    header('Location:index.php');
    echo $check. "<br>". $email;

    mysqli_close($con);
?>