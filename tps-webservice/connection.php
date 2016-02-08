<?php
    // connection file 
    $server = "localhost";
    $user = "root";
    $pass = "";
    $db = "tps";
    
    // connect to db
    $mysqli = new mysqli($server, $user, $pass, $db);
    $mysqli->set_charset("utf8");

    if(mysqli_connect_errno()){
        printf("Connect failed: %s\n", mysqli_connect_error());
        exit();
    }
?>