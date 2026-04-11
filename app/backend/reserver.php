<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

if ($conn->connect_error) die(json_encode(["status" => "error"]));

$hab_id = isset($_POST['habitat_id']) ? intval($_POST['habitat_id']) : (isset($_GET['habitat_id']) ? intval($_GET['habitat_id']) : 0);
$date_res = isset($_POST['date']) ? $conn->real_escape_string($_POST['date']) : (isset($_GET['date']) ? $conn->real_escape_string($_GET['date']) : '');
$heure_res = isset($_POST['heure']) ? intval($_POST['heure']) : (isset($_GET['heure']) ? intval($_GET['heure']) : 0);
$puissance = isset($_POST['puissance']) ? intval($_POST['puissance']) : (isset($_GET['puissance']) ? intval($_GET['puissance']) : 0);

$date_jour = date('Y-m-d');
$heure_actuelle = date('H');

if ($date_res < $date_jour || ($date_res == $date_jour && $heure_res <= $heure_actuelle)) {
    die(json_encode([
        "status" => "error",
        "message" => "Le créneau est déjà passé !"
    ]));
}

$sql = "INSERT INTO reservation (habitat_id, date_res, heure_res, puissance_watts) VALUES ($hab_id, '$date_res', $heure_res, $puissance)";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error", "message" => $conn->error]);
}
$conn->close();
?>