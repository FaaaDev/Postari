<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $table = $_POST['table'];
    $param = $_POST['param'];
    $where = $_POST['where'];

    require_once 'connection.php';

    $sql = "DELETE FROM $table WHERE $param = '$where'";

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menghapus Data";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menghapus Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
