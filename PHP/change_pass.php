<html lang="en">
<head><title>Main Page</title></head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/innoview.css">

<body>
<h1>You are Logged in</h1>

<?php
	session_start();
	//echo $_SESSION['username'];
	if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    echo "Welcome to the member's area, " . $_SESSION['username'] . "! <br>";
	} else {
	    echo "Please log in first to see this page.";
	}

	if (isset($_POST['change']))
	{
		//echo "mphke";
		if (empty($_POST['username'])||empty($_POST['old_password'])||($_POST['username']!=$_SESSION['username'])||(sha1($_POST['username'].$_POST['old_password'])!= $_SESSION['password']))
		{
			echo " Username and old password not valid";
		}
		else
		{
			echo "correct username and Old password";
			if (empty($_POST['password1']) || empty($_POST['password2']))
			{
				$error = " New Password is not printed";
				echo $error;
			}
			else
			{
				$pass1 =$_POST['password1'];
				$pass2 =$_POST['password2'];
				if ($pass1==$pass2){
					$new_pass = sha1($_SESSION['username'].$pass1);
					$query_change_password = "update innoview set password=\"".$new_pass."\" where username=\"".$_SESSION['username']."\"";
					//echo $query_change_password;

					$servername = "localhost";
					$username = "gpant7";
					$password = "rakos";
					$dbname = "test";
					$conn = new mysqli($servername, $username, $password, $dbname);
					if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
					};

					$result=$conn->query($query_change_password);
					echo "Password changed!";
				}
				else{
					echo " The password was not typed the same in the two fields";
				}
			}
		}
	}
	// if (isset($_POST['logout'])){
	// 	echo "goint back";
	// 	session_destroy();
	// 	header('location:start.php');
	// }else{
	// 	echo "staying";
	// }
?>
<h2>Change your password</h2> 
	<div class="container">
		<form name="form" method="post" action="change_pass.php">
			<div>
				<input type="text" name="username" placeholder="Username">
			<input type="password" name="old_password" placeholder="old_password">
			</div>
			<div>
			<input type="password" name="password1" placeholder=" New password">
			<input type="password" name="password2" placeholder="Re-type new password">
			</div>
			<div>
				<input type="submit" name="change" class = "button" value="Submit">
			</div>
		</form>
		<form method="post" action="start.php">
			<input type="submit" name="logout" class="button" value="Log out">
		</form>
	</div>

</body>
</html>