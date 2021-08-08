<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST'){

    $user_id = $_POST['user_id'];
    $layanan = $_POST['layanan'];

    require_once('connection.php');

    if (empty($layanan)) {
        $sql = "SELECT * FROM anak WHERE user_id = '$user_id'";
    } else {
        $sql = "SELECT a.id, a.user_id, a.nama, a.birthdate, a.gender FROM anak a INNER JOIN layanan b ON a.user_id = b.user_id WHERE b.nama = '$layanan'";
    }

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
} else if ($_SERVER['REQUEST_METHOD'] == 'GET'){

    require_once('connection.php');

    $sql = "SELECT * FROM anak";

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