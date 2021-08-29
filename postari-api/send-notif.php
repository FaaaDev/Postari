<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    $message = [
        'body'              => 'Halo ada yang bisa saya bantu',
        'title'             => 'Petugas Kesehatan',
        'notification_type' => 'Test'
    ];

    
    $id = 'ckCmAThdQc2KpiR005y8Em:APA91bEok7qRXOEjzFpeyMXs2urJ972OqcYY8L5hdEqBH_j-tkxznry3fnANilVjsMCdUUhCL0zmHuBjbPt5zJ_gC4yobJY6ZITkkQOf7E27wb-BP2B47uiaUR6JqokINj-87G4qw3Lq';
    $fields = array(
        'registration_ids' => array(
            $id
        ),
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
    $result = curl_exec($ch);
    if ($result === FALSE) {
        echo 'gagal';
        die('Oops! FCM Send Error: ' . curl_error($ch));
    } else {
        echo 'berhasil';
    }
    curl_close($ch);
}
