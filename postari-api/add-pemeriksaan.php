<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $tanggal = $_POST['tanggal'];
    $keluhan = $_POST['keluhan'];
    $tekanan_darah = $_POST['tekanan_darah'];
    $bb_ibu = $_POST['bb_ibu'];
    $umur_hamil = $_POST['umur_hamil'];
    $tinggi_fundus = $_POST['tinggi_fundus'];
    $letak_janin = $_POST['letak_janin'];
    $denyut_janin = $_POST['denyut_janin'];
    $kaki_bengkak = $_POST['kaki_bengkak'];
    $pem_laboratorium = $_POST['pem_laboratorium'];
    $tindakan = $_POST['tindakan'];
    $nasihat = $_POST['nasihat'];
    $pemeriksa = $_POST['pemeriksa'];
    $tanggal_periksa_kembali = $_POST['tanggal_periksa_kembali'];

    require_once 'connection.php';

    $sql = "INSERT INTO `pemeriksaan_bumil`(`id`, `user_id`, `tanggal`, `keluhan`, `tekanan_darah`, `bb_ibu`, `umur_hamil`, `tinggi_fundus`, `letak_janin`, `denyut_janin`, `kaki_bengkak`, `pem_laboratorium`, `tindakan`, `nasihat`, `pemeriksa`, `tanggal_periksa_kembali`) 
                                    VALUES (null,'$user_id','$tanggal','$keluhan','$tekanan_darah','$bb_ibu','$umur_hamil','$tinggi_fundus','$letak_janin','$denyut_janin','$kaki_bengkak','$pem_laboratorium','$tindakan','$nasihat','$pemeriksa','$tanggal_periksa_kembali')";

    if (mysqli_query($conn, $sql)) {
        $result["status"] = true;
        $result["message"] = "Berhasil Menambah Data Pemeriksaan";

        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["status"] = false;
        $result["message"] = "Gagal Menambah Data";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
