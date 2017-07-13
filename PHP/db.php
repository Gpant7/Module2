<?php//----------- Database Creation & connection ----------//
	
	$servername = "localhost";
	$username = "gpant7";
	$password = "rakos";
	$dbname = "test";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	};

	$username1 ="John"; 
	$pass1 ="tra";
	$password1 = sha1($username1.$pass1);

	$username2 ="Nick"; 
	$pass2 ="schi";
	$password2 = sha1($username2.$pass2);

	$username3 ="geokost"; 
	$pass3 ="kostakis1994";
	$password3 = sha1($username3.$pass3);

	$query_insert = "Insert into innoview values(\"$username1\",\"$password1\",\"new entry 1\"),(\"$username2\",\"$password2\",\"new entry 2\"),(\"$username3\",\"$password3\",\"eisai malakas\")";
	


	//	check insertion
	if ($conn->query($query_insert) === TRUE) {
	    echo "New record created successfully";
	} else {
	    echo "Error: " . $query_insert . "<br>" . $conn->error;
	}


	//---------- Select * from table -------------------//
	$query_select = "Select * from innoview";

	$result = $conn->query($query_select);
	$i=0;
	if ($result->num_rows>0){
		//echo $result->fetch_assoc()["password"]."<br>";	
		while($row = $result->fetch_assoc()){
			//echo $i++ . " --->";
			//echo sha1($i)." !<br> ";
			echo "username: " . $row["username"]. "  |  password: " . $row["password"] ."<br>";
		}
	}
	else{
		echo "0 results";
	}	
?>