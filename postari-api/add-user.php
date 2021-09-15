<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $username = $_POST['username'];
    $password = $_POST['password'];
    $role = $_POST['role'];
    $image = $_POST['image'];

    require_once 'connection.php';

    if (empty($_POST['id'])){
        $sql = "INSERT INTO user (user_id, username, password, role, image) VALUES ('$user_id', '$username', '$password', '$role',  '$image')";
    } else {
        $id = $_POST['id'];
        $sql = "UPDATE user SET user_id = '$user_id', username = '$username', password = '$password', role = '$role' WHERE user_id = '$id'";
    }
    

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah User";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
