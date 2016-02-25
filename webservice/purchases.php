<?php
ini_set('display_errors', 1);
    include 'connection.php';

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
            $strQuery = "SELECT * FROM purchases WHERE (DATE Like '". $year ."-%')";
            getPurchasesByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM purchases WHERE (DATE Like '". $year ."-". $month ."-%')";
            getPurchasesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM purchases WHERE (DATE = '" . $date . "' )";
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

        $jsonObject = json_decode($jsonString);

        $purchases = $jsonObject->{'Purchases'};

        $query = "INSERT INTO purchases (`ITEM`, `AMOUNT`, `PURCHASE_PRICE`, `SELLER`, `DATE`, `ACCOUNT_ID`)
            VALUES ";

        for($i = 0; $i < count($purchases); $i++){
          $item = $purchases[$i]->{'item'};
          $amount = $purchases[$i]->{'amount'};
          $purchasePrice = $purchases[$i]->{'purchasePrice'};
          $seller = $purchases[$i]->{'seller'};
          $date = $purchases[$i]->{'date'};
          $accountId = $purchases[$i]->{'accountId'};

          $query .= "('" . $item . "', '" . $amount . "', '" . $purchasePrice . "', '" . $seller . "', '" . $date . "', '" . $accountId . "')";

          if($i < (count($purchases) - 1)){
            $query .= ",";
          }
        }

        $purchasesResult = $mysqli->query($query);



        if($purchasesResult === TRUE){
          $result = TRUE;
        }
        else{
          $result = FALSE;
        }

        echo json_encode(array('result' => $result), JSON_FORCE_OBJECT);
    }

    mysqli_close($mysqli);
?>
