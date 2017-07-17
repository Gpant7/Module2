<?php 
   session_start();
    $_SESSION['retrieve'] = 'true';
    echo $_SESSION['retrieve'];
    if ($_SESSION['retrieve'] = 'true'){
        header("location:tinymce.php");
    }
    // if ($_SESSION['retrieve'] == true){
    //     $query_retrieve = "Select text from innoview where username=\"".$_SESSION['username']."\"";
    //     $res = $conn->query($query_retrieve);

    //     if ($res->num_rows>0){
    //         while($row = $res->fetch_assoc()){
    //             echo $row["text"];
    //         }
    //     }
    // }
    // else{
    //     echo $_SESSION['retrieve'];
    //     echo "bla bla";
    // }
?> 


<!-- <form action="<?php $_SESSION['retrieve']=true;  ?>">
          <input type="submit" name="retrieve" value="Retrieve Text" >
    </form> -->
    <!-- <form action="<?php $_SESSION['retrieve']=false;  ?>">
          <input type="submit" name="blank" value="Clear the Text" >
    </form> -->

