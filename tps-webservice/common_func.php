<?php 
    function updateAccounts($obj, $value, $date){
        global $mysqli;
        
        $jsonResponse;
        
        // convert string Date to array
        $date = explode('-', $date);
        // update the Day to 1
        $date[2] = 1;
        // combine the array to create new Date String
        $date = implode('-', $date);
        
        
        $strQuery = "INSERT INTO Accounts 
                        (" . $obj . ", Date)
                     VALUES
                        ('" . $value . "', '" . $date . "')
                     ON DUPLICATE KEY UPDATE
                        " . $obj . " = " .  $obj ." + " . $value . "";
                        
                        
        $result = $mysqli->query($strQuery) or die($mysqli->error);
        if($result === TRUE){
            $jsonResponse = TRUE;
        }
        else{
            $jsonResponse = FALSE;
        }    
        
        return $jsonResponse;      
    }
?>