<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET'){

    require_once('connection.php');

    $sql = "SELECT * FROM lokasi_posyandu";

    $query = mysqli_query($conn,$sql);

    if (mysqli_num_rows($query) > 0) {
        while ($row = mysqli_fetch_object($query)) {
            $data['status'] = true;
            $data['data'][] = $row;
        }
    } else {
        $data['status'] = false;
        $data['data'] = "Data not Found";
    }

    print_r(json_encode($data));
}
?>