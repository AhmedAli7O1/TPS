<?php
ini_set('display_errors', 1);
    include 'connection.php';

    if($_POST['method'] === "getOutgoings"){
        getOutgoings($_POST['json']); //call getOutgoings method
    }
    elseif($_POST['method'] === "addOutgoings"){
        addOutgoings($_POST['json']);
    }

    function getOutgoings($json){
        global $mysqli;

        //get params from json object
        $jsonObj = json_decode($json);
        $date = $jsonObj->{'date'};
        $style = $jsonObj->{'style'};

        $undate = strtotime($date);

        $year = date('Y', $undate);
        $month = date('m', $undate);
        $day = date('d', $undate);

        if($style === "YEAR"){
            $strQuery = "SELECT * FROM OUTGOINGS WHERE (DATE Like '". $year ."-%')";
            getOutgoingsByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM OUTGOINGS WHERE (DATE Like '". $year ."-". $month ."-%')";
            getOutgoingsByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM OUTGOINGS WHERE (DATE = '" . $date . "' )";
            getOutgoingsByQuery($strQuery);
        }
    }

    function getOutgoingsByQuery($query){
        global $mysqli;

        // get array of orders
        $result = $mysqli->query($query);

        $outgoings = array();

        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $outgoings[] = $row;
            }

            echo json_encode(array('Outgoings' => $outgoings));
        }
    }

    function addOutgoings($jsonString){
        global $mysqli;

        $jsonObj = json_decode($jsonString);   // json object contains outgoings array
        $outgoings = $jsonObj->{'Outgoings'};  // outgoings array

        $query = "INSERT INTO OUTGOINGS (`DETAILS`, `VALUE`, `DATE`, `ACCOUNT_ID`)
            VALUES ";

        $totalValue = 0;
        for($i = 0; $i < count($outgoings); $i++){

          $details = $outgoings[$i]->{'details'};
          $value = $outgoings[$i]->{'value'};
          $date = $outgoings[$i]->{'date'};
          $accountId = $outgoings[$i]->{'accountId'};

          $totalValue += $value;

          $query .= "('" . $details . "', '" . $value . "', '" . $date . "', '" . $accountId . "')";

          if($i < (count($outgoings) - 1)){
            $query .= ",";
          }
        }

        $outgoingsResult = $mysqli->query($query);

        if($outgoingsResult === TRUE){
          $result = TRUE;
        }
        else {
          $result = FALSE;
        }

        echo json_encode(array('result' => $result), JSON_FORCE_OBJECT);
    }

    mysqli_close($mysqli);
?>
