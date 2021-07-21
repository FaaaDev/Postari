<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $nama_posyandu = $_POST['nama_posyandu'];
    $alamat = $_POST['alamat'];

    require_once 'connection.php';

    $sql = "INSERT INTO lokasi_posyandu (id, nama_posyandu, alamat) VALUES (null, '$nama_posyandu', '$alamat')";

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Lokasi";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
