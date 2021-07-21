<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $nama_ibu = $_POST['nama_ibu'];
    $nama_suami = $_POST['nama_suami'];
    $alamat = $_POST['alamat'];
    $posyandu = $_POST['posyandu'];
    $layanan = $_POST['layanan'];

    require_once 'connection.php';

    $sql = "INSERT INTO orang_tua (user_id, nama_ibu, nama_suami, alamat, posyandu) VALUES ('$user_id', '$nama_ibu', '$nama_suami', '$alamat',  '$posyandu')";

    if (mysqli_query($conn, $sql)) {
        if (!empty($layanan)) {
            $value = explode(",", $layanan);
            $success = 0;
            foreach($value as $param){
                $sql = "INSERT INTO layanan (id, user_id, nama) VALUES (null, '$user_id', '$param')";
                if (mysqli_query($conn, $sql)) {
                    $success++;
                    if ($success == count($value)) {
                        $result["status"] = true;
                        $result["message"] = "Berhasil Menambah Data dan Layanan";
                
                        echo json_encode($result);
                        mysqli_close($conn);
                    }
                }
            } 
        } else {
            $result["status"] = true;
            $result["message"] = "Berhasil Menambah Data";
    
            echo json_encode($result);
            mysqli_close($conn);
        }
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
