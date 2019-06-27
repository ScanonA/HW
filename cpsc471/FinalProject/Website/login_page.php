<?php session_start();?>

<html>
<body>
<h1>Log-In</h1>
<a href="index.php">Home</a>
<a href="sign-up.php">Sign-up</a>
<form action="login.php" method="post">
   E-mail: <input type="email" name="email"><br>
   Password: <input type="password" name="password"><br>
   <input type="submit" value="Log In">
</form>

</body>
</html>
