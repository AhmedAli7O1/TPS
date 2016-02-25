<?php
  include 'connection.php';    //handle connection to mysql DB

  //call the requested function
  if($_POST['method'] == "getAccounts"){
    getAccounts(); //call getAccounts method
  }
  elseif ($_POST['method'] == "addAccount") {
    addAccount($_POST['json']);
  }

  //get all accounts
  function getAccounts(){
    global $mysqli;

    $strQuery = "SELECT * FROM accounts";
    $accounts = getAccountsByQuery($strQuery);

    //return accounts as a json Object
    echo json_encode(array('Accounts' => $accounts));
  }

  //get accounts from db filterd by parameter strQuery
  function getAccountsByQuery($strQuery){
    global $mysqli;

    // get array of accounts
    $result = $mysqli->query($strQuery);

    $accounts = array();

    if($result->num_rows > 0){
      while($row = $result->fetch_assoc()){
        $accounts[] = $row;
      }
    }
    return $accounts;
  }

  function addAccount($json){
    global $mysqli;
    //get params from json object
    $jsonObj = json_decode($json);

    $oldBalance = $jsonObj->{'oldBalance'};
    $date = $jsonObj->{'date'};
    //create new account for the current Month
    $strQuery = "INSERT INTO accounts (DATE, BALANCE) VALUES ('" . $date . "', '" . $oldBalance . "')";
    $result = $mysqli->query($strQuery);
    if($result == TRUE){
      $response = TRUE;
    }else {
      $response = FALSE;
    }
    echo json_encode(array('result' => $response), JSON_FORCE_OBJECT);
  }
 ?>
