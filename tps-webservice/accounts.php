<?php
  include 'connection.php';

  if($_POST['method'] === "getAccounts"){
    getAccounts($_POST['json']); //call getOrders method
  }
  elseif ($_POST['method'] === "getDates") {
    getDates();
  }

  function getAccounts($json){
    global $mysqli;

    //get params from json object
    $jsonObj = json_decode($json);

    $year = $jsonObj->{'year'};

    $strQuery = "SELECT * FROM Accounts WHERE (Date Like '". $year ."-%')";
    getAccountsByQuery($strQuery);
  }

  function getAccountsByQuery($query){
    global $mysqli;

    // get array of orders
    $result = $mysqli->query($query);

    $accounts = array();

    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){
            $accounts[] = $row;
        }

        echo json_encode(array('Accounts' => $accounts));
    }
  }

  function getDates(){
    global $mysqli;

    $strQuery = "SELECT Date FROM Accounts";

    $result = $mysqli->query($strQuery);

    $dates = array();

    if($result->num_rows > 0){
      while($row = $result->fetch_assoc()){
        $dates[] = $row;
      }
    }
    echo json_encode(array('Dates' => $dates, 'size' => $result->num_rows));


  }
?>
