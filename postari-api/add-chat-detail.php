<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $chat_id = $_POST['chat_id'];
    $sender = $_POST['sender'];
    $message = $_POST['message'];

    require_once 'connection.php';

    $sql = "INSERT INTO chat (id, id_chat, sender, message) VALUES (null, '$chat_id', '$sender', '$message')";

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
