<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $token = $_POST['token'];

    require_once 'connection.php';

    $sql = "INSERT INTO token (id, user_id, token) VALUES (null, '$user_id', '$token')";

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Mengirim Pesan";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Mengirim Pesan";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
