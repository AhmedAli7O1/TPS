<?php
ini_set('display_errors', 1);
    include 'connection.php';
    include 'accounts.php';

    if($_POST['method'] === "getIncomes"){
        getIncomes($_POST['json']); //call getIncomes method
    }
    elseif($_POST['method'] === "addIncomes"){
        addIncomes($_POST['json']);
    }

    function getIncomes($json){
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
            $strQuery = "SELECT * FROM Incomes WHERE (Date Like '". $year ."-%')";
            getIncomesByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM Incomes WHERE (Date Like '". $year ."-". $month ."-%')";
            getIncomesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM Incomes WHERE (Date = '" . $date . "' )";
            getIncomesByQuery($strQuery);
        }
    }

    function getIncomesByQuery($query){
        global $mysqli;

        // get array of Incomes
        $result = $mysqli->query($query);

        $Incomes = array();

        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $Incomes[] = $row;
            }

            echo json_encode(array('Incomes' => $Incomes));
        }
    }

    function addIncomes($jsonString){
        global $mysqli;

        $jsonResponse = "";
        $incomesResult = FALSE;
        $accountsResult = FALSE;

        $jsonObj = json_decode($jsonString);
        $incomes = $jsonObj->{'Incomes'};


        $query = "INSERT INTO Incomes
                    (`Details`, `Value`, `IsDebt`, `Date`)
                  VALUES ";

        $totalValue = 0;
        for($i = 0; $i < count($incomes); $i++){
            $details = $incomes[$i]->{'details'};
            $value = $incomes[$i]->{'value'};
            $isDebt = $incomes[$i]->{'isDebt'};
            $date = $incomes[$i]->{'date'};
            $totalValue += $value;
            $query .= " ('" . $details . "', '" . $value . "', '" . $isDebt . "','" . $date . "')";

            if($i < (count($incomes) - 1)){
              $query .= ",";
            }
        }

        $result = $mysqli->query($query);

        if($result === TRUE){
            $incomesResult = TRUE;
            $accountsResult = updateAccounts("TotalIncomes", $totalValue, $date);
        }

        $jsonResponse = json_encode(array('IncomesResult' => $incomesResult, 'AccountsResult' => $accountsResult), JSON_FORCE_OBJECT);

        echo $jsonResponse;
    }

    mysqli_close($mysqli);
?>
