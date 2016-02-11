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
            $strQuery = "SELECT * FROM Outgoings WHERE (Date Like '". $year ."-%')";
            getOutgoingsByQuery($strQuery);
        } 
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM Outgoings WHERE (Date Like '". $year ."-". $month ."-%')";
            getOutgoingsByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM Outgoings WHERE (Date = '" . $date . "' )";
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

        $jsonResponse = "";
        $processResult = 0;

        $jsonObj = json_decode($jsonString);   // json object contains outgoings array
        $outgoings = $jsonObj->{'Outgoings'};  // outgoings array
        
        foreach($outgoings as $outgoing){
        
            $details = $outgoing->{'details'};
            $value = $outgoing->{'value'};
            $date = $outgoing->{'date'};
        
            $query = "INSERT INTO Outgoings (`Details`, `Value`, `Date`) 
                VALUES ('" . $details . "', '" . $value . "', '" . $date . "')";
                
            $result = $mysqli->query($query);
        
            if($result === TRUE){
                $processResult ++;
            } 
        }
        
                
        $jsonResponse = json_encode(array('result' => $processResult), JSON_FORCE_OBJECT);
        echo $jsonResponse;  
    }
    
    mysqli_close($mysqli);
?>