<?php session_start();?>

<html>
<body>
<h1>Sign-up</h1>
<a href="index.php">Home</a> </a>
<a href="login_page.php">Log-in</a>
<form action="signin.php" method="post">
   E-mail: <input type="email" name="email"><br>
   Password: <input type="password" name="password"><br>
   Username: <input type= "text" name="name"><br>
   <input type="submit" value="Sign Up">
</form>

</body>
</html>
