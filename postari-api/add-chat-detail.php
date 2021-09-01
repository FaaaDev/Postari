<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $messages = $_POST['messages'];
    $sender_id = $_POST['sender_id'];
    $receiver_id = $_POST['receiver_id'];

    require_once 'connection.php';

    $sql = "INSERT INTO messages (id, messages, sender_id, receiver_id) VALUES (null, '$messages', '$sender_id', '$receiver_id')";

    if (mysqli_query($conn, $sql)) {
        $sql2 = "SELECT a.token, b.username FROM token a INNER JOIN user b ON a.user_id = b.user_id WHERE a.user_id = '$receiver_id'";

        $query = mysqli_query($conn, $sql2);
        if (mysqli_num_rows($query) > 0) {
            $id = array();
            $username = '';
            while ($row = mysqli_fetch_object($query)) {
                array_push($id, $row->token);
                $username = $row->username;
            }

            $message = [
                'body'              => $messages,
                'title'             => $username,
                'notification_type' => 'Test'
            ];

            $fields = array(
                'registration_ids'  => $id,
                'data'              => $message,
                'priority'          => 'high',
            );
            $fields = json_encode($fields);

            //FCM API end-point
            $url = 'https://fcm.googleapis.com/fcm/send';
            //api_key in Firebase Console -> Project Settings -> CLOUD MESSAGING -> Server key
            $server_key = 'AAAAz8bcJOk:APA91bGP3SXXTp-x_aBvNKj8LNyuyFW-sqiWFN1wxG2MRAcPvfXcbFuGp-dx6F89-U3aW7tPOpyQv-5knOcQK09ENRRzMVEwHKKqRrvGREsLTkzUUE30git2x7a5dl8zTzjEhvRHxaAN';
            //header with content_type api key
            $headers = array(
                'Content-Type:application/json',
                'Authorization:key=' . $server_key
            );
            //CURL request to route notification to FCM connection server (provided by Google)
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
            $results = curl_exec($ch);
            if ($results === FALSE) {
                $result["status"] = true;
                $result["message"] = "Gagal mengirim notifikasi";
                die('Oops! FCM Send Error: ' . curl_error($ch));
            } else {
                $result["status"] = true;
                $result["message"] = "Berhasil";
            }
            curl_close($ch);
        } else {
            $result["status"] = true;
            $result["message"] = "Tidak ada token ditemukan";
        }

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Mengirim Pesan";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
