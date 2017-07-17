<?php
    session_start();
    //$_SESSION['retrieve']=true;
    //echo $_SESSION['retrieve'];
    $servername = "localhost";
    $username = "gpant7";
    $password = "rakos";
    $dbname = "test";
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    };

     $allowedTags='<p><strong><em><u><h1><h2><h3><h4><h5><h6><img>';
     $allowedTags.='<li><ol><ul><span><div><br><ins><del>';  
    // Should use some proper HTML filtering here.
    if( isset($_POST['myTextArea']) && ($_POST['myTextArea'])!='') 
    {
        //echo "1111111111";
        $sHeader = '<h2>Submission accomplished. Keep going!</h2>';
        $sContent = strip_tags(stripslashes($_POST['myTextArea']),$allowedTags);
        //echo $sContent;

        $query_insert_text = "update innoview set text=\"".$sContent."\" where username=\"".$_SESSION['username']."\"";
        $result=$conn->query($query_insert_text);
    } 
    else 
    {
        if ($_SESSION['retrieve']==='false'){
            //echo "22222222222";
            $sHeader = '<h2>Edit your thoughts</h2>';
            $sContent = '<p></p>';
            $sContent = strip_tags(stripslashes($sContent));
        }
        else
        {
            //echo "33333333333";
            $sHeader = '<h2>Your text was retrieved succesfully</h2>';
            $sContent = '';       
            $query_retrieve = "Select text from innoview where username=\"".$_SESSION['username']."\"";
            $res = $conn->query($query_retrieve);

            if ($res->num_rows>0)
            {
                while($row = $res->fetch_assoc()){
                    //echo $row["text"];
                    $sContent.=$row["text"];
                }
            }
        } 
    } 
?>
<html>
<head>
<title>My tinyMCE Editor</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/innoview.css">

<script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>

<script language="javascript" type="text/javascript" src="/javascript/tinymce.js"></script>
<script type="text/javascript">
  tinymce.init({
    selector: "#myTextArea",
    theme:'modern',
    //width:600,
    height:300,
    plugins: ['advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker',
      'searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking',
      'save table contextmenu directionality emoticons template paste textcolor'
    ],
    toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons'
});

</script>
</head>
<body>
<h1>Welcome to TinyEmc Editor</h1>
<?php echo $sHeader;?>
<div class="container">
     <form method="post" action="">
        <div>
        <textarea id="myTextArea" name="myTextArea" rows="20" cols="90"><?php echo $sContent; ?>
         </textarea>
        </div>
        <input type="submit" name="save" value="Submit" />
        <input type="reset" name="reset" value="Reset" />
    </form/>
    <div id="logout">
        <form action="logout.php"> If you want to logout, just push the button
           <p><input type="submit" name="logout" value="Log Out" /></p>
        </form>
    </div>
    <div id="retrieve">
        <form action="retrieve_text.php"> If you want to see your text, push the button
           <p><input type="submit" name="retrieve" value="Retrieve Text" /></p>
        </form>
    </div>
</div>
<?php

        // echo $_SESSION['username'];
        // echo $_POST['myTextArea'];
        
        // $storing_text = mysql_real_escape_string($sContent);
        // echo $storing_text;
?>
</body>
</html>