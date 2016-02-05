<?php
ini_set('display_errors', 1);
    include 'connection.php';
    
    // call the passes in function 
    if(function_exists($_GET['method'])){
        $_GET['method']($_GET['date'], $_GET['style']);
    }
    
    function getOrders($date, $style){
        global $mysqli;
        
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
    
    mysqli_close($mysqli);
?>