<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

$hab_id = intval($_GET['habitat_id']);
$date = $conn->real_escape_string($_GET['date']);
$heure = intval($_GET['heure']);
$puissance = intval($_GET['puissance']); // 🔥 La nouveauté !

$sql1 = "INSERT INTO reservation (habitat_id, date_res, heure_res, puissance_watts) VALUES ($hab_id, '$date', $heure, $puissance)";
$sql2 = "UPDATE habitat SET eco_coins = eco_coins + 50 WHERE id = $hab_id";

if ($conn->query($sql1) === TRUE && $conn->query($sql2) === TRUE) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
$conn->close();
?>