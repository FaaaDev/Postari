<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_lokasi = $_POST['id_lokasi'];
    $kegiatan = $_POST['kegiatan'];
    $tanggal = $_POST['tanggal'];

    require_once 'connection.php';

    $sql = "INSERT INTO jadwal (id, id_lokasi, kegiatan, tanggal) VALUES (null, '$id_lokasi', '$kegiatan', '$tanggal')";

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Jadwal";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
