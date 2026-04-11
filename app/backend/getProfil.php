<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

if ($conn->connect_error) die(json_encode(["status" => "error", "message" => "Erreur BDD"]));

if (!isset($_GET['habitat_id'])) {
    die(json_encode(["status" => "error", "message" => "habitat_id manquant"]));
}

$hab_id = intval($_GET['habitat_id']);
$profil_data = array();

$sql_coins = "SELECT eco_coins FROM habitat WHERE id = $hab_id";
$res_coins = $conn->query($sql_coins);
if ($res_coins && $res_coins->num_rows > 0) {
    $profil_data['eco_coins'] = $res_coins->fetch_assoc()['eco_coins'];
} else {
    $profil_data['eco_coins'] = 0;
}

$sql_conso = "SELECT SUM(puissance_watts) as total_conso FROM equipement WHERE habitat_id = $hab_id";
$res_conso = $conn->query($sql_conso);
$profil_data['consommation_totale'] = ($res_conso && $res_conso->num_rows > 0) ? $res_conso->fetch_assoc()['total_conso'] : 0;
if ($profil_data['consommation_totale'] == null) $profil_data['consommation_totale'] = 0;

$date_jour = date('Y-m-d');
$heure_actuelle = date('H');

$sql_res = "SELECT date_res, heure_res, puissance_watts FROM reservation
            WHERE habitat_id = $hab_id
            AND (date_res > '$date_jour' OR (date_res = '$date_jour' AND heure_res >= $heure_actuelle))
            ORDER BY date_res ASC, heure_res ASC";
$res_res = $conn->query($sql_res);

$profil_data['reservations'] = array();
if ($res_res && $res_res->num_rows > 0) {
    while($row = $res_res->fetch_assoc()) {
        $profil_data['reservations'][] = $row;
    }
}

$profil_data['status'] = "success";
echo json_encode($profil_data);
$conn->close();
?>