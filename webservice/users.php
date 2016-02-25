<?php
    include 'connection.php';

    // call the passes in function
    if($_POST['method'] === "login"){
        login($_POST['json']);
    }

    function login($json){
        global $mysqli;

        $params = json_decode($json);
        $uName = $params->{'uName'};
        $uPass = $params->{'uPass'};

        // check if user exist
        $strQuery = sprintf("SELECT `NAME`, `TITLE`, `AUTH`, `LAST_EDIT`, `LAST_LOGGED`, `SEC_KEY`
                                FROM users WHERE (NAME = '%s' AND PASSWORD = '%s')", $uName, $uPass);

        $result = $mysqli->query($strQuery);

        if($result->num_rows > 0){
            $row = $result->fetch_assoc();
            $row["SEC_KEY"] = rand();

            // add key to db
            $insQuery = sprintf("UPDATE users SET SEC_KEY='%s' WHERE (NAME='%s')", $row["SEC_KEY"], $row["NAME"]);
            $mysqli->query($insQuery);
            echo json_encode($row);
        }
    }

    mysqli_close($mysqli);
?>
