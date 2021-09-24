<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_anak = $_POST['id_anak'];
    $bb_anak = $_POST['bb_anak'];
    $tb_anak = $_POST['tb_anak'];
    $tanggal = $_POST['tanggal'];

    require_once 'connection.php';

    if (empty($_POST['id'])) {
        $sql = "INSERT INTO penimbangan (id, id_anak, bb_anak, tb_anak, tanggal) VALUES (null, '$id_anak', '$bb_anak', '$tb_anak', '$tanggal')";
    } else {
        $id = $_POST['id'];
        $sql = "UPDATE penimbangan SET id_anak = '$id_anak', bb_anak = '$bb_anak', tb_anak = '$tb_anak', tanggal = '$tanggal' WHERE id = $id";
    }


    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Penimbangan";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
