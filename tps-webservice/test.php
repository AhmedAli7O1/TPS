<?php
  include 'connection.php';

  $strQuery = "SELECT LAST_INSERT_ID(ID) FROM Sales";
  $result = $mysqli->query($strQuery);

  $result = $result->fetch_assoc();
  foreach ($result as $res) {
    echo $res . "    ";
  }
  echo mysql_insert_id();
 ?>
