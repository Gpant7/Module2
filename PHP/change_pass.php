<html lang="en">
<head><title>Main Page</title></head>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/innoview.css">

<body>
<h1>You are Logged in</h1>

<?php
	session_start();
	//echo $_SESSION['password'];
	if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    echo "<h2>Welcome to the member's area, " . $_SESSION['username'] . "!</h2> <br>";
	} else {
	    echo "Please log in first to see this page. <br>";
	}

	if (isset($_POST['change']))
	{
		//echo "mphke";
		if (empty($_POST['username'])||empty($_POST['old_password'])||($_POST['username']!=$_SESSION['username'])||(sha1($_POST['username'].$_POST['old_password'])!= $_SESSION['password']))
		{
			echo " Username or old password not valid";
		}
		else
		{
			echo "Correct username and Old password<br>";
			if (empty($_POST['password1']) || empty($_POST['password2']))
			{
				$error = " You should Double-print the New Password !";
				echo $error;
			}
			else
			{
				$pass1 =$_POST['password1'];
				$pass2 =$_POST['password2'];
				if ($pass1==$pass2){
					$new_pass = sha1($_SESSION['username'].$pass1);
					$_SESSION['password']=$new_pass;
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

<h4>Change your password</h4> 
	<div class="container">
		<form name="form" method="post" action="change_pass.php">
			<div> Type your Username and  Password
				<p><input type="text" name="username" placeholder="Username"></p>
				<p><input type="password" name="old_password" placeholder="old_password"></p>
			</div>
			<div> Now type twice the new Password
			<p><input type="password" name="password1" placeholder=" New password"></p>
			<p><input type="password" name="password2" placeholder="Re-type new password"></p>
			</div>
			<div>
				<input type="submit" name="change" class = "button" value="Submit">
			</div>
		</form>
		<div id="logout">
			<form name="logout" method="post" action="logout.php">
				If you want to logout, just push the button<p><input type="submit" name="logout" class="button" value="Log out"></p>
			</form>
		</div>
		<div id="text_editor">
		<form method="post" action="text_editor.php">
			Go to the text editor
			<p><input type="submit" name="text_editor" class="button" value="Text Editor"></p>
		</form>
		</div>
	</div>

</body>
</html>