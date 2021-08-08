<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET'){

    require_once('connection.php');

    $sql = "SELECT a.`user_id`,a.`nama_ibu`,a.`nama_suami`,a.`alamat`,b.`nama_posyandu` FROM orang_tua a INNER JOIN lokasi_posyandu b ON a.`posyandu` = b.`id`";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['data'][] = "";
    }

    print_r(json_encode($data));
} else if ($_SERVER['REQUEST_METHOD'] == 'POST'){
    $layanan = $_POST['layanan'];

    require_once('connection.php');

    $sql = "SELECT a.`user_id`,a.`nama_ibu`,a.`nama_suami`,a.`alamat`,b.`nama_posyandu` FROM orang_tua a INNER JOIN lokasi_posyandu b ON a.`posyandu` = b.`id` INNER JOIN layanan c ON a.`user_id` = c.`user_id` WHERE c.`nama` = '$layanan'";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['data'][] = "";
    }

    print_r(json_encode($data));
}
?>