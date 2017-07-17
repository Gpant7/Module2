<?php
	session_start();
	//echo "Welcome ".$_SESSION['username']."!<br> I hope you will enjoy writing!";
	$_SESSION['retrieve'] = 'false';
	header("location:tinymce.php");
?>
