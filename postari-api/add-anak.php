<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $user_id = $_POST['user_id'];
    $nama = $_POST['nama'];
    $birthdate = $_POST['birthdate'];
    $gender = $_POST['gender'];

    require_once 'connection.php';

    if (empty($_POST['id'])){
        $sql = "INSERT INTO anak (user_id, nama, birthdate, gender) VALUES ('$user_id', '$nama', '$birthdate', '$gender')";
    } else {
        $id = $_POST['id'];
        $sql = "UPDATE anak SET nama = '$nama', birthdate = '$birthdate', gender = '$gender' WHERE id = $id";
    }

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Data Anak";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
