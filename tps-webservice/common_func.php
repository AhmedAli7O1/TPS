<?php
    function updateAccounts($obj, $value, $balance, $date){
        global $mysqli;

        $jsonResponse;

        // convert string Date to array
        $date = explode('-', $date);
        // update the Day to 1
        $date[2] = 1;
        // combine the array to create new Date String
        $date = implode('-', $date);

        // get last row in account
        $strQuery = "SELECT * FROM Accounts ORDER BY ID DESC LIMIT 1";

        // if this is the row we're looking for update it
        if($result = $mysqli->query($strQuery)){
          $result = $result->fetch_object();
          //check if this is the row we're looking for or not
          if($result->Date === $date){
            //update this row with new values
            return update_account($result->ID, $obj, $value, $balance);
          }
          else{
            // if not crate new row with the balance of this row as
            // old balance for the new one
            return insert_account($obj, $value, $balance, $date, $result->OldBalance);
          }
        }
        else {
          //seems this is the first row in accounts table
          //so we'll just create new account row
          return insert_account($obj, $value, $balance, $date, 0);
        }
    }

    function update_account($id, $obj, $value, $balance){
      global $mysqli;

      $strQuery = "UPDATE Accounts SET
                  " . $obj . " = " . $obj . " + " . $value . " ,
                  Balance = Balance + " . $value . " WHERE ID=" . $id . " ";

      $result = $mysqli->query($strQuery) or die($mysqli->error);
      if($result === TRUE){
          $jsonResponse = TRUE;
      }
      else{
          $jsonResponse = FALSE;
      }

      return $jsonResponse;
    }

    function insert_account($obj, $value, $balance, $date, $OldBalance){
      global $mysqli;

      $strQuery = "INSERT INTO Accounts
                  (" . $obj . ", OldBalance, Balance, Date)
                  VALUES
                  ('" . $value . "', '" . $OldBalance . "',
                   '" . $balance . "', '" . $date . "')";


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
