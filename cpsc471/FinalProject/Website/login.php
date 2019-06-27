<?php session_start();?>

<?php
$email = $_POST["email"];
$password = $_POST["password"];
//echo $email. "<br>". $password. "<br>";

// Create connection
$con=mysqli_connect("localhost","root","471-my-root","mysocial");

//Check connection
if (mysqli_connect_errno($con))
    {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    //$sql1 = "SELECT Email_address FROM LOGGED_IN WHERE '$email' = Email_address";
    //echo $email_entered['Email_address']. "<br>";
    //$sql2 = "SELECT Password FROM LOGGED_IN WHERE '$password' = Password";
    //echo $password_entered. "<br>";
    $sql = "SELECT Name, ID FROM LOGGED_IN WHERE '$email' = Email_address AND '$password' = LOGGED_IN.Password";
    $verify = mysqli_query($con, $sql);
    if (!$verify) {
        die('Error: ' . mysqli_error($con));
        }
    while ($verify_result = mysqli_fetch_assoc($verify)) {
        $name = $verify_result['Name']. "<br>";
        $id = $verify_result['ID']. "<br>";
    }

    //echo $name. "<br>";

    if(mysqli_num_rows($verify)==0)
        {
            echo "incorrect credentials";
            ?>
            <a href="login_page.php">Try Again</a>
            <?php
        }else {
            $_SESSION['email'] = $email;
            $_SESSION['password'] = $password;
            $_SESSION['name'] = $name;
            $_SESSION['id'] = $id;
            //echo "Welcome ". $name. "<br>";//. $_SESSION['email']. "<br>". $_SESSION['password']. "<br>";
            header('Location:index.php');
        }
   

    mysqli_close($con);
?>