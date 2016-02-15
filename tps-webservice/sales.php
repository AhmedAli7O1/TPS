<?php
ini_set('display_errors', 1);
    include 'connection.php';
    include 'accounts.php';

    if($_POST['method'] === "getOrders"){
        getOrders($_POST['json']); //call getOrders method
    }
    elseif($_POST['method'] === "addOrder"){
        addOrder($_POST['json']);
    }

    function getOrders($json){
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
            $strQuery = "SELECT * FROM Orders WHERE (Date Like '". $year ."-%')";
            getSalesByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM Orders WHERE (Date Like '". $year ."-". $month ."-%')";
            getSalesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM Orders WHERE (Date = '" . $date . "' )";
            getSalesByQuery($strQuery);
        }
    }

    function getSalesByQuery($query){
        global $mysqli;

        // get array of orders
        $result = $mysqli->query($query);

        $orders = array();

        if($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $orders[] = $row;
            }

            // get array of items in all orders
            $items = array();

            foreach($orders as $order){
                $query = sprintf("SELECT * FROM Sales WHERE (OrderID = '%d' )", $order['ID']);
                $result = $mysqli->query($query);
                while($row = $result->fetch_assoc()){
                    $items[] = $row;
                }
            }

            $data = array();
            $data['Orders'] = $orders;
            $data['Items'] = $items;

            echo json_encode($data);
        }
    }

    function addOrder($jsonString){
        global $mysqli;

        $order = json_decode($jsonString);

        $customer = $order->{'customer'};
        $discount = $order->{'discount'};
        $totalPrice = $order->{'totalPrice'};
        $paid = $order->{'paid'};
        $date = $order->{'date'};
        $items = $order->{'items'};

        $orderQuery = "INSERT INTO Orders (`Customer`, `Discount`, `TotalPrice`, `Paid`, `Date`)
            VALUES ('" . $customer . "', '" . $discount . "',
                '" . $totalPrice . "', '" . $paid . "', '" . $date . "')";

        $mysqli->begin_transaction();                //begin Transaction
        $orderResult = $mysqli->query($orderQuery);  //excute Order query

        //get last order ID to store with items
        $idQuery = "SELECT ID FROM Orders ORDER BY ID DESC LIMIT 1";
        $idResult = $mysqli->query($idQuery);
        $orderID = $idResult->fetch_object();
        $orderID = $orderID->ID;
        //create 'Add Items' Query
        $itemsQuery = "INSERT INTO Sales (`Name`, `Amount`, `Price`, `Discount`, `SoldPrice`, `Paid`, `OrderID`, `Date`)
            VALUES ";

        for($i = 0; $i < count($items); $i++){
          $name = $items[$i]->{'name'};              // Customer Name
          $amount = $items[$i]->{'amount'};          // Amount of Items
          $price = $items[$i]->{'price'};            // Item Price
          $discount = $items[$i]->{'discount'};      // Discount
          $soldPrice = $items[$i]->{'soldPrice'};    // Price after discount
          $paid = $items[$i]->{'paid'};              // money paid
          $date = $items[$i]->{'date'};              // date of sale


          $itemsQuery .= "('" . $name . "', '" . $amount . "','" . $price . "', '" . $discount . "', '" . $soldPrice . "',
              '" . $paid . "', '" . $orderID . "', '" . $date . "')";

          if($i < (count($items) - 1)){
            $itemsQuery .= ",";
          }
        }

        $itemsResult = $mysqli->query($itemsQuery);  //excute items query
        $accountsResult = updateAccounts("TotalSales", $paid, $date); //update accounts

        if($orderResult === TRUE && $itemsResult === TRUE && $accountsResult === TRUE){
          $mysqli->commit();
          $salesResult = TRUE;
        }
        else {
          $mysqli->rollback();
          $salesResult = FALSE;
        }

        echo json_encode(array('result' => $salesResult), JSON_FORCE_OBJECT);
    }

    mysqli_close($mysqli);
?>
