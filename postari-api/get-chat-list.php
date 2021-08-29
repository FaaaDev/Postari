<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){
    $user_id = $_POST['user_id'];
    $role = $_POST['role'];

    require_once('connection.php');

    if ($role == 'ortu'){
        $sql = "SELECT a.id, a.id_sender, a.id_receiver, b.message FROM chat_list a INNER JOIN chat b ON a.id = b.id_chat WHERE a.id_sender = '$user_id' ORDER BY b.id DESC LIMIT 1";
    } else {
        $sql = "SELECT a.id, a.id_sender, a.id_receiver, b.message FROM chat_list a INNER JOIN chat b ON a.id = b.id_chat WHERE a.id_receiver = '$user_id' ORDER BY b.id DESC LIMIT 1";
    }

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