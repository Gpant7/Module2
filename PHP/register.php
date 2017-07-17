<html lang="en">
<head><title>Login Page</title></head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/innoview.css">

<body>
<h1> Create new account</h1>
	<h2>Enter Username and Password</h2> 
	<div class="container">
	
	<?php
	session_start();
	$servername = "localhost";
	$username = "gpant7";
	$password = "rakos";
	$dbname = "test";

	// $_SESSION['username']='';
	// $_SESSION['password']='';

	if (isset($_POST['register']))
	{
		//echo "mphke";
		if (empty($_POST['username1']) || empty($_POST['username2'])|| ($_POST['username1']!=$_POST['username2'])){
			echo "Username is invalid";
		}
		else
		{
			if (empty($_POST['password1']) || empty($_POST['password2'])|| ($_POST['password1']!=$_POST['password2']))
			{
				echo "Password is invalid";
			}
			else
			{

				$user =$_POST['username1'];
				$pass =sha1($user.$_POST['password1']);

				// Create connection
				$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				};

				$query_insert = "Insert into innoview values(\"$user\",\"$pass\",\"\")";
				echo $query_insert;

				$result=$conn->query($query_insert);
				
					$_SESSION['loggedin'] = true;
					$_SESSION['username'] =$user;
					$_SESSION['password'] =$pass;
					$_SESSION['connection']=$conn;
					//echo $_SESSION['loggedin'].$_SESSION['username'].$_SESSION['password'];
					header('location:change_pass.php');
					

			}
		}
	}
	else{
			//echo "Unable to get in";
			//echo $_POST['username'];
	}

?>
		<form name="form" method="post" action="">
			<div> Double type your Username
				<p><input type="text" name="username1" placeholder="Username"></p>
				<p><input type="text" name="username2" placeholder="Username"></p>
			</div>
			<div> Double type your Password
			<p><input type="password" name="password1" placeholder=" New password"></p>
			<p><input type="password" name="password2" placeholder="Re-type new password"></p>
			</div>
			<div>
				<input type="submit" name="register" class = "button" value="Register">
			</div>
		</form>
		<form action="start.php">
			<div id="goback">
				If you want to go back, push the button
				<p><input type="submit" name="goback" class = "button" value="Go Back"></p>
			</div>
		</form>

	
	</div>



</body>
</html>