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
        $processResult = 0;

        $Incomes = json_decode($jsonString);  
        
        $details = $Incomes->{'details'};
        $value = $Incomes->{'value'};
        $date = $Incomes->{'date'};
        
        $query = "INSERT INTO Incomes (`Details`, `Value`, `Date`) 
            VALUES ('" . $details . "', '" . $value . "', '" . $date . "')";
                
        $result = $mysqli->query($query);
        
        if($result === TRUE){
            $processResult = 1;
        }
        else{
            $processResult = 0;
        }
        
        $jsonResponse = json_encode(array('result' => $processResult), JSON_FORCE_OBJECT);
        
        echo $jsonResponse;
    }
    
    mysqli_close($mysqli);
?>