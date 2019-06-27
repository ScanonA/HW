<?php session_start();?>

<html>
<body>
<h1>Creating A Thread</h1>
<a href="index.php">Home</a>
<form action="createthread_backend.php" method="post">
   Thread Name: <input type="text" name="thread_name"><br>
   <input type="submit" value="Create">
</form>

</body>
</html>