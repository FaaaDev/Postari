<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){

    $user_id = $_POST['user_id'];

    require_once('connection.php');

    $sql = "SELECT * FROM user WHERE user_id = '$user_id'";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'] = $row;
        }
    } else {
        $data['status'] = false;
        $data['result'] = "Data not Found";
    }

    print_r(json_encode($data));
}
?>