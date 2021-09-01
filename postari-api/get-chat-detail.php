<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){

    $receiver_id = $_POST['receiver_id'];
    $sender_id = $_POST['sender_id'];

    require_once('connection.php');

    $sql = "SELECT * FROM messages 
            WHERE (receiver_id = '$receiver_id' AND sender_id = '$sender_id') 
            OR (receiver_id = '$sender_id' AND sender_id = '$receiver_id')";

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