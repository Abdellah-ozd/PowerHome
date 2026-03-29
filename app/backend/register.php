<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Connexion BDD échouée"]));
}

$nom = $conn->real_escape_string($_GET['name']);
$email = $conn->real_escape_string($_GET['email']);
$mdp = $conn->real_escape_string($_GET['password']);
$floor = intval($_GET['floor']);
$area = floatval($_GET['area']);

$sql1 = "INSERT INTO habitat (floor, area, resident_name, appliances_count, eco_coins)
         VALUES ($floor, $area, '$nom', 0, 0)";

if ($conn->query($sql1) === TRUE) {
    $habitat_id = $conn->insert_id;


    $sql2 = "INSERT INTO `user` (email, password, habitat_id)
             VALUES ('$email', '$mdp', $habitat_id)";

    if ($conn->query($sql2) === TRUE) {
        echo json_encode(["status" => "success", "message" => "Compte et habitat créés"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Erreur création user"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Erreur création habitat"]);
}

$conn->close();
?>