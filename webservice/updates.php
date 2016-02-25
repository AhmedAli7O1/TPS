<?php
  include 'connection.php';    //handle connection to mysql DB

  if($_POST['method'] === "getLastUpdate"){
      getLastUpdate(); //call getLastUpdate method
  }

  function getLastUpdate(){
    global $mysqli;

    $query = "SELECT * FROM updates ORDER BY ID DESC LIMIT 1";

    $result = $mysqli->query($query);

    $update = $result->fetch_assoc();

    echo json_encode(array('LastUpdate' => $update));
  }

  ?>
