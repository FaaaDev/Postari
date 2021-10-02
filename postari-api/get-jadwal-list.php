<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET'){

    require_once('connection.php');

    $sql = "SELECT a.id, a.id_lokasi, b.url, b.nama_posyandu, a.kegiatan, a.tanggal FROM jadwal a INNER JOIN lokasi_posyandu b ON a.id_lokasi = b.id";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['data'] = [];
    }

    print_r(json_encode($data));
}
?>