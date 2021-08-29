<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_sender = $_POST['id_sender'];
    $id_receiver = $_POST['id_receiver'];

    require_once 'connection.php';

    $sql = "INSERT INTO chat_list (id, id_sender, id_receiver) VALUES (null, '$id_sender', '$id_receiver')";

    if (mysqli_query($conn, $sql)) {

        $sql2 = "SELECT id FROM chat_list WHERE id_sender = '$id_sender' AND id_receiver = '$id_receiver'";

        $query = mysqli_query($conn, $sql2);
        if (mysqli_num_rows($query) > 0) {
            while ($row = mysqli_fetch_object($query)) {
                $result["status"] = true;
                $result["message"] = $row->id;
            }
        }

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
