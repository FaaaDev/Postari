<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $user_id = $_POST['user_id'];

    require_once('connection.php');

    $sql = "SELECT a.id, a.messages, a.sender_id, a.receiver_id, 
            b.username AS sender_name, c.username AS receiver_name,
            b.image AS sender_image, c.image AS receiver_image,
            b.role AS sender_role, c.role AS receiver_role
            FROM (SELECT messages.* FROM messages, (
                SELECT MAX(id) as lastid FROM messages 
                WHERE (messages.receiver_id = '$user_id' 
                    OR messages.sender_id = '$user_id') 
                GROUP BY CONCAT(
                    LEAST(messages.receiver_id,messages.sender_id),'.',
                    GREATEST(messages.receiver_id,messages.sender_id)
                )
            ) as conversations 
            WHERE id = conversations.lastid) AS a
            INNER JOIN user b
            ON a.sender_id=b.user_id
            INNER JOIN user c
            ON a.receiver_id=c.user_id
            ORDER BY a.id DESC";

    $query = mysqli_query($conn, $sql);

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
