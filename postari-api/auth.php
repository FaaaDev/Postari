<?php 

    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once 'connection.php';

        $user_id = $_POST['user_id'];
        $password = $_POST['password'];
        // $id_pass = md5()

        //++++++++++++++++ username dan pass
        if(empty($user_id)){
            $ray = array(
                "status"=>false,
                "message"=>"User Tidak Boleh Kosong");
            echo json_encode($ray);

        } elseif (empty($password)){
            $ray = array(
                "status"=>false,
                "message"=>"Password Tidak Boleh Kosong");
            echo json_encode($ray);

        } else {
            //================= cek user menggunakan username
            $user_query = "SELECT * FROM user WHERE user_id = '$user_id'";
            $user_sql = mysqli_query($conn, $user_query);
            $user_ray = array();
            while ($user_row = mysqli_fetch_array($user_sql)) {
                array_push($user_ray, array(
                    "user_id" => $user_row['user_id']
                ));
            }

            if (empty($user_ray)){
                // == cek user dengan menggunakan pass
                $user_query = "SELECT * FROM user WHERE password = '$password'";
                $user_sql = mysqli_query($conn, $user_query);
                $user_ray = array();
                while ($user_row = mysqli_fetch_array($user_sql)) {
                    array_push($user_ray, array(
                    "password" => $row['password']
                ));
            }

            if (empty($user_ray)){
                $ray = array(
                    "status"=>false,
                    "message"=>"User Anda Tidak Terdaftar");
                echo json_encode($ray);
            } else {
                // === cek pass dengan no hp
                // == cek pass 
                //$id_pass = md5
                $query = "SELECT * FROM user WHERE user_id = '$user_id' AND password = '$password'";
                $sql = mysqli_query($conn, $query);
                $ray = array();
                while ($row = mysqli_fetch_array($sql)){
                    $ray = array(
                        "status"=>true,
                        "message"=>"Berhasil Login",
                        "data"=>array(
                            "user_id" => $row['user_id'],
                            "username" => $row['username'],
                            "password" => $row['password'],
                            "role" => $row['role'],
                            "image"=> $row['image']
                        )
                    );
                }

                if(empty($ray)){
                    $ray = array(
                        "status"=>false,
                        "message"=>"Password Anda Salah");
                    echo json_encode($ray);

                } else {
                    echo json_encode($ray);
                    mysqli_close($conn);
                }
            }

        } else {
            // ====== cek password dengan username
             // ==== cek password
             // $id_pas = md5
                $query = "SELECT * FROM user WHERE user_id = '$user_id' AND password = '$password'";
                $sql = mysqli_query($conn, $query);
                $ray = array();
                while ($row = mysqli_fetch_array($sql)) {
                    $ray = array(
                        "status"=>true,
                        "message"=>"Berhasil Login",
                        "data"=>array(
                            "user_id" => $row['user_id'],
                            "username" => $row['username'],
                            "password" => $row['password'],
                            "role" => $row['role'],
                            "image"=> $row['image']
                        )
                    );
                }

                if (empty($ray)) {
                    $ray = array (
                        "status" => false,
                        "message" => "Password Anda Salah");
                    echo json_encode($ray);

                } else {
                    echo json_encode($ray);
                    mysqli_close($conn);
                }

        }
      //=========
    }

} else {
    $ray = array(
        "status"=>false,
        "message"=>"Bad Request");
    echo json_encode($ray);
     
}
