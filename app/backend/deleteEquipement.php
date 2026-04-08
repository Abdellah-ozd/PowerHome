<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

$hab_id = isset($_GET['habitat_id']) ? intval($_GET['habitat_id']) : 0;
$nom = isset($_GET['nom']) ? $conn->real_escape_string($_GET['nom']) : '';

$sql = "DELETE FROM equipement WHERE habitat_id = $hab_id AND nom = '$nom' LIMIT 1";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["status" => "success", "message" => "Équipement supprimé"]);
} else {
    echo json_encode(["status" => "error"]);
}
$conn->close();
?>