<?php
ini_set('display_errors', 1);
    include 'connection.php';

    if($_POST['method'] == "getOrders"){
        getOrders($_POST['json']); //call getOrders method
    }
    elseif($_POST['method'] == "addOrder"){
        addOrder($_POST['json']);
    }
    elseif ($_POST['method'] == "searchItems") {
      searchItems($_POST['json']);
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
            $strQuery = "SELECT * FROM orders WHERE (DATE Like '". $year ."-%')";
            getSalesByQuery($strQuery);
        }
        elseif($style === "MONTH"){
            $strQuery = "SELECT * FROM orders WHERE (DATE Like '". $year ."-". $month ."-%')";
            getSalesByQuery($strQuery);
        }
        elseif($style === "DAY"){
            $strQuery = "SELECT * FROM orders WHERE (DATE = '" . $date . "' )";
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
                $query = sprintf("SELECT * FROM sales WHERE (ORDER_ID = '%d' )", $order['ID']);
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
        $price = $order->{'price'};
        $paid = $order->{'paid'};
        $date = $order->{'date'};
        $items = $order->{'items'};
        $accountId = $order->{'accountId'};

        $orderQuery = "INSERT INTO orders (`CUSTOMER`, `PRICE`, `PAID`, `DATE`, `ACCOUNT_ID`)
            VALUES ('" . $customer . "','" . $price . "', '" . $paid . "', '" . $date . "', '" . $accountId . "')";

        $mysqli->begin_transaction();                //begin Transaction
        $orderResult = $mysqli->query($orderQuery);  //excute Order query

        //create 'Add Items' Query
        $itemsQuery = "INSERT INTO sales (`ITEM_NAME`, `AMOUNT`, `PRICE`, `PAID`, `PURCHASES_VALUE`, `ORDER_ID`)
            VALUES ";

        for($i = 0; $i < count($items); $i++){
          $itemName = $items[$i]->{'name'};              // Customer Name
          $amount = $items[$i]->{'amount'};          // Amount of Items
          $price = $items[$i]->{'price'};            // Item Price
          $paid = $items[$i]->{'paid'};
          $purchaseValue = $items[$i]->{'purchaseValue'};

          $itemsQuery .= "('" . $itemName . "', '" . $amount . "',
          '" . $price . "', '" . $paid . "', '" . $purchaseValue . "', LAST_INSERT_ID())";

          if($i < (count($items) - 1)){
            $itemsQuery .= ",";
          }
        }

        $itemsResult = $mysqli->query($itemsQuery);  //excute items query

        if($orderResult === TRUE && $itemsResult === TRUE){
          $mysqli->commit();
          $salesResult = TRUE;
        }
        else {
          echo $orderResult;
          echo $itemsResult;
          $mysqli->rollback();
          $salesResult = FALSE;
        }

        echo json_encode(array('result' => $salesResult), JSON_FORCE_OBJECT);
    }

    function searchItems($json){
      global $mysqli;
      $search = json_decode($json);

      $searchFor = $search->{'searchFor'};

      $query = "SELECT * FROM sales WHERE (ITEM_NAME Like '%" . $searchFor . "%')";
      $items = array();
      $result = $mysqli->query($query) or die($mysqli->error);
      if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){
            $items[] = $row;
        }
      }
      echo json_encode(array('Items' => $items));
    }

    mysqli_close($mysqli);
?>
