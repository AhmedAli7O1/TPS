<?php
ini_set('display_errors', 1);
    include 'connection.php';
    include 'accounts.php';

    if($_POST['method'] === "getWithdrawals"){
        getWithdrawals($_POST['json']); //call getWithdrawals method
    }
    elseif($_POST['method'] === "addWithdrawals"){
        addWithdrawals($_POST['json']);
    }

    function getWithdrawals($json){
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
            $strQuery = "SELECT * FROM Withdrawals WHERE (Date Like '". $year ."-%')";
            getWithdrawalsByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM Withdrawals WHERE (Date Like '". $year ."-". $month ."-%')";
            getWithdrawalsByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM Withdrawals WHERE (Date = '" . $date . "' )";
            getWithdrawalsByQuery($strQuery);
        }
    }

    function getWithdrawalsByQuery($query){
        global $mysqli;

        // get array of orders
        $result = $mysqli->query($query);

        $withdrawals = array();

        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $withdrawals[] = $row;
            }

            echo json_encode(array('Withdrawals' => $withdrawals));
        }
    }

    function addWithdrawals($jsonString){
        global $mysqli;

        $jsonResponse = "";
        $withdrawalsResult = FALSE;
        $accountsResult = FALSE;

        $jsonObject = json_decode($jsonString);
        $withdrawals = $jsonObject->{'Withdrawals'};

        $query = "INSERT INTO Withdrawals (`Details`, `Value`, `Date`)
            VALUES ";

        $totalValue = 0;
        for($i = 0; $i < count($withdrawals); $i++){
          $details = $withdrawals[$i]->{'details'};
          $value = $withdrawals[$i]->{'value'};
          $date = $withdrawals[$i]->{'date'};
          $totalValue += $value;

            $query .= "('" . $details . "', '" . $value . "', '" . $date . "')";

            if($i < (count($withdrawals) - 1)){
              $query .= ",";
            }
        }

        $result = $mysqli->query($query);

        if($result === TRUE){
            $withdrawalsResult = TRUE;
            $accountsResult = updateAccounts("TotalWithdrawals", $totalValue, $date);
        }

        $jsonResponse = json_encode(array('WithdrawalsResult' => $withdrawalsResult, 'AccountsResult' => $accountsResult), JSON_FORCE_OBJECT);

        echo $jsonResponse;
    }

    mysqli_close($mysqli);
?>
