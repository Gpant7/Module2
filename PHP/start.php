<html lang="en">
<head><title>Login Page</title></head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/innoview.css">

<body>
<h1> Welcome to the Innoview PHP test</h1>
	<h2>Enter Username and Password</h2> 
	<div class="container">


<?php
	
	$servername = "localhost";
	$username = "gpant7";
	$password = "rakos";
	$dbname = "test";

	// $_SESSION['username']='';
	// $_SESSION['password']='';
	// $error = " ";

	if (isset($_POST['login'])){
		//echo "mphke";
		if (empty($_POST['username']) || empty($_POST['password'])){
			$error = $error." Username or Password is invalid";
			echo $error;
		}
		else{
			$user =$_POST['username'];
			$pass =sha1($user.$_POST['password']);

			// Create connection
			$conn = new mysqli($servername, $username, $password, $dbname);
			// Check connection
			if ($conn->connect_error) {
			    die("Connection failed: " . $conn->connect_error);
			};

			$query_search = "select * from innoview where username=\"".$user."\" and password=\"".$pass."\"";
			//echo $query_search."<br>";

			$result=$conn->query($query_search);
			if ($result->num_rows>0){
				//exit();
				session_start();
				$_SESSION['loggedin'] = true;
				$_SESSION['username'] =$user;
				$_SESSION['password'] =$pass;
				$_SESSION['connection']=$conn;
				//echo $_SESSION['loggedin'].$_SESSION['username'].$_SESSION['password'];
				header('location:change_pass.php');
				
				while($row = $result->fetch_assoc()){
				//echo $i++ . " --->";
				//echo sha1($i)." !<br> ";
				//echo "username: ". $row["username"]. "  |  password: " . $row["password"] ."<br>";
					echo " Hello ".$row["username"];
				}
			}
			else{
				echo "Username or password don't exist";
			}
		}
	}
	else{
		echo "Unable to get in";
		//echo $_POST['username'];
	}

?>


	
		<form name="form" method="post" action="start.php">
			<input type="text" name="username" placeholder="Username">
			<input type="password" name="password" placeholder="Password">
			<input type="submit" name="login" class = "button" value="Login">
		</form>
	
		<div class="login-help">
			<a href="#">Register</a> - <a href="#">Forgot Password</a>
		</div>
	</div>



</body>
</html>