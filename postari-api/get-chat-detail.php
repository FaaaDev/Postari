<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){

    $id = $_POST['id'];

    require_once('connection.php');

    $sql = "SELECT * FROM chat WHERE id = '$id'";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['data'] = [];
    }

    print_r(json_encode($data));
}
?>