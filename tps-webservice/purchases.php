<?php
ini_set('display_errors', 1);
    include 'connection.php';
    include 'accounts.php';

    if($_POST['method'] === "getPurchases"){
        getPurchases($_POST['json']);    //call getPurchases method
    }
    elseif($_POST['method'] === "addPurchases"){
        addPurchases($_POST['json']);
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

    function addPurchases($jsonString){
        global $mysqli;

        $jsonResponse = "";
        $purchasesResult = FALSE;
        $accountResult = FALSE;

        $jsonObject = json_decode($jsonString);
        $purchases = $jsonObject->{'Purchases'};

        $query = "INSERT INTO Purchases (`Item`, `Amount`, `PurchasePrice`, `Seller`, `Date`)
            VALUES ";

        $totalValue = 0;
        for($i = 0; $i < count($purchases); $i++){
          $item = $purchases[$i]->{'item'};
          $amount = $purchases[$i]->{'amount'};
          $purchasePrice = $purchases[$i]->{'purchasePrice'};
          $seller = $purchases[$i]->{'seller'};
          $date = $purchases[$i]->{'date'};
          $totalValue += $purchasePrice;
          $query .= "('" . $item . "', '" . $amount . "', '" . $purchasePrice . "', '" . $seller . "', '" . $date . "')";

          if($i < (count($purchases) - 1)){
            $query .= ",";
          }
        }

        $result = $mysqli->query($query);

        if($result === TRUE){
            $purchasesResult = TRUE;
            $accountResult = updateAccounts("TotalPurchases", $totalValue, $date);
        }

        $jsonResponse = json_encode(array('PurchasesResult' => $purchasesResult, 'AccountsResult' => $accountResult), JSON_FORCE_OBJECT);

        echo $jsonResponse;
    }

    mysqli_close($mysqli);
?>
