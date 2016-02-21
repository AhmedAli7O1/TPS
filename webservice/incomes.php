<?php
ini_set('display_errors', 1);
    include 'connection.php';

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
            $strQuery = "SELECT * FROM INCOMES WHERE (DATE Like '". $year ."-%')";
            getIncomesByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM INCOMES WHERE (DATE Like '". $year ."-". $month ."-%')";
            getIncomesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM INCOMES WHERE (DATE = '" . $date . "' )";
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

        $jsonObj = json_decode($jsonString);
        $incomes = $jsonObj->{'Incomes'};


        $query = "INSERT INTO INCOMES
                    (`DETAILS`, `VALUE`, `IS_DEBT`, `DATE`, `ACCOUNT_ID`)
                  VALUES ";

        for($i = 0; $i < count($incomes); $i++){
            $details = $incomes[$i]->{'details'};
            $value = $incomes[$i]->{'value'};
            $isDebt = $incomes[$i]->{'isDebt'};
            $date = $incomes[$i]->{'date'};
            $accountId = $incomes[$i]->{'accountId'};

            $query .= " ('" . $details . "', '" . $value . "', '" . $isDebt . "','" . $date . "', '" . $accountId . "')";

            if($i < (count($incomes) - 1)){
              $query .= ",";
            }
        }

        $incomesResult = $mysqli->query($query);

        if($incomesResult === TRUE){
          $result = TRUE;
        }
        else{
          $result = FALSE;
        }

        echo json_encode(array('result' => $result), JSON_FORCE_OBJECT);
    }

    mysqli_close($mysqli);
?>
