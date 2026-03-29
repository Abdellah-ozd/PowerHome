<?php
$host = "localhost";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307;

// Connexion à la base
$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    http_response_code(500);
    die(json_encode(["status" => "error", "message" => "Erreur de connexion BDD"]));
}

if(isset($_GET['floor']) && isset($_GET['area']) && isset($_GET['resident_name']) && isset($_GET['appliances_count'])) {

    // Récupération des données
    $floor = $_GET['floor'];
    $area = $_GET['area'];
    $resident_name = $_GET['resident_name'];
    $appliances_count = $_GET['appliances_count'];

    // Commande SQL
    $sql = "INSERT INTO habitat (floor, area, resident_name, appliances_count)
            VALUES ('$floor', '$area', '$resident_name', '$appliances_count')";

    // Exécution
    if ($conn->query($sql) === TRUE) {
        http_response_code(200);
        echo json_encode(["status" => "success", "message" => "Nouveau locataire ajouté au tier-quar !"]);
    } else {
        http_response_code(500);
        echo json_encode(["status" => "error", "message" => "Erreur SQL : " . $conn->error]);
    }

} else {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Il manque des infos mon gars !"]);
}

$conn->close();
?>