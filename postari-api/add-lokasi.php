<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $nama_posyandu = $_POST['nama_posyandu'];
    $alamat = $_POST['alamat'];
    $url = $_POST['url'];

    require_once 'connection.php';

    if (empty($_POST['id'])) {
        $sql = "INSERT INTO lokasi_posyandu (id, nama_posyandu, alamat, url) VALUES (null, '$nama_posyandu', '$alamat', '$url')";
    } else {
        $id = $_POST['id'];
        $sql = "UPDATE lokasi_posyandu SET nama_posyandu = '$nama_posyandu', alamat = '$alamat', url = "$url" WHERE id = $id ";
    }

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
