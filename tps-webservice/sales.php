<?php
ini_set('display_errors', 1);
    include 'connection.php';
    
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

        $jsonResponse = "";
        $processResult = 0;

        $order = json_decode($jsonString);  
        
        $customer = $order->{'customer'};
        $discount = $order->{'discount'};
        $totalPrice = $order->{'totalPrice'};
        $paid = $order->{'paid'};
        $date = $order->{'date'};
        $items = $order->{'items'};
        
        $query = "INSERT INTO Orders (`Customer`, `Discount`, `TotalPrice`, `Paid`, `Date`) 
            VALUES ('" . $customer . "', '" . $discount . "',
                '" . $totalPrice . "', '" . $paid . "', '" . $date . "')";
                
        $result = $mysqli->query($query);
        
        if($result === TRUE){
            //get last order ID to store with items 
            $query = "SELECT ID FROM Orders ORDER BY ID DESC LIMIT 1";
            $result = $mysqli->query($query);
        
            $value = $result->fetch_object();
            
            $itemCount = 0;
            
            foreach($items as $it){
                $name = $it->{'name'};              // Customer Name
                $amount = $it->{'amount'};          // Amount of Items
                $price = $it->{'price'};            // Item Price
                $discount = $it->{'discount'};      // Discount
                $soldPrice = $it->{'soldPrice'};    // Price after discount
                $paid = $it->{'paid'};              // money paid
                $orderID = $value->ID;              // order id 
                $date = $it->{'date'};              // date of sale
            
                $query = "INSERT INTO Sales (`Name`, `Amount`, `Price`, `Discount`, `SoldPrice`, `Paid`, `OrderID`, `Date`) 
                    VALUES ('" . $name . "', '" . $amount . "','" . $price . "', '" . $discount . "', '" . $soldPrice . "',
                        '" . $paid . "', '" . $orderID . "', '" . $date . "')";
                
                $result = $mysqli->query($query);
                if($result === TRUE){
                    $itemCount++;
                }
            }
            
            if($itemCount === count($items)){
                $processResult = 1;
            }
            else{
                $processResult = 0;
            }
        }
        else{
            $processResult = 0;
        }
        
        $jsonResponse = json_encode(array('result' => $processResult), JSON_FORCE_OBJECT);
        
        echo $jsonResponse;
    }
    
    mysqli_close($mysqli);
?>