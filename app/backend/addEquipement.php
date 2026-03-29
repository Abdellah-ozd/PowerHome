<?php
header('Content-Type: application/json');
$host = "127.0.0.1";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307;

$conn = new mysqli($host, $user, $password, $dbname, $port);
if ($conn->connect_error) die(json_encode(["status" => "error", "message" => "Erreur de co"]));

// On récupère les vraies données du formulaire Android
$hab_id = isset($_GET['habitat_id']) ? intval($_GET['habitat_id']) : 1;
$nom = isset($_GET['nom']) ? $conn->real_escape_string($_GET['nom']) : 'Appareil';
$puissance = isset($_GET['puissance']) ? intval($_GET['puissance']) : 0;

$sql = "INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES ($hab_id, '$nom', $puissance)";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["status" => "success", "message" => "Equipement ajouté en BDD"]);
} else {
    echo json_encode(["status" => "error", "message" => $conn->error]);
}
$conn->close();
?>