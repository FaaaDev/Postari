<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_anak = $_POST['id_anak'];
    $type = $_POST['type'];
    $keterangan = $_POST['keterangan'];
    $tanggal = $_POST['tanggal'];

    require_once 'connection.php';

    if (empty($_POST['id'])) {
        $sql = "INSERT INTO imunisasi (id, id_anak, type, keterangan, tanggal) VALUES (null, '$id_anak', '$type', '$keterangan', '$tanggal')";
    } else {
        $id = $_POST['id'];
        $sql = "UPDATE imunisasi SET id_anak = '$id_anak', type = '$type', keterangan = '$keterangan', tanggal = '$tanggal' WHERE id = $id";
    }

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Data Imunisasi";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
