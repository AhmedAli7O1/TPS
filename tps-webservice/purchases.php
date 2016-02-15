<?php
ini_set('display_errors', 1);
    include 'connection.php';
    include 'accounts.php';
    
    if($_POST['method'] === "getPurchases"){
        getPurchases($_POST['json']);    //call getPurchases method
    }
    elseif($_POST['method'] === "addPurchase"){
        addPurchase($_POST['json']);
    }
    
    function getPurchases($json){
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
            $strQuery = "SELECT * FROM Purchases WHERE (Date Like '". $year ."-%')";
            getPurchasesByQuery($strQuery);
        } 
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM Purchases WHERE (Date Like '". $year ."-". $month ."-%')";
            getPurchasesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM Purchases WHERE (Date = '" . $date . "' )";
            getPurchasesByQuery($strQuery);
        }
    }
    
    function getPurchasesByQuery($query){
        global $mysqli;

        // get array of orders 
        $result = $mysqli->query($query);
        
        $purchases = array();
        
        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $purchases[] = $row; 
            }
            
            echo json_encode(array('Purchases' => $purchases)); 
        }
    }
    
    function addPurchase($jsonString){
        global $mysqli;

        $jsonResponse = "";
        $purchasesResult = FALSE;
        $accountResult = FALSE;
        
        $purchase = json_decode($jsonString);  
        
        $item = $purchase->{'item'};
        $amount = $purchase->{'amount'};
        $purchasePrice = $purchase->{'purchasePrice'};
        $seller = $purchase->{'seller'};
        $date = $purchase->{'date'};
        
        $query = "INSERT INTO Purchases (`Item`, `Amount`, `PurchasePrice`, `Seller`, `Date`) 
            VALUES ('" . $item . "', '" . $amount . "', '" . $purchasePrice . "', '" . $seller . "', '" . $date . "')";
                
        $result = $mysqli->query($query);
        
        if($result === TRUE){
            $purchasesResult = TRUE;
            $accountResult = updateAccounts("TotalPurchases", $purchasePrice, $date);
        }
        
        $jsonResponse = json_encode(array('PurchasesResult' => $purchasesResult, 'AccountsResult' => $accountResult), JSON_FORCE_OBJECT);
        
        echo $jsonResponse;
    }
    
    mysqli_close($mysqli);
?>