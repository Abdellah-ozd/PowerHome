<?php
$host = "localhost";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307; // Toujours la bonne porte frérot

$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    die(json_encode(["error" => "Connexion crashée : " . $conn->connect_error]));
}

// On récupère l'ID de l'habitat envoyé par l'appli Android
if (isset($_GET['habitat_id'])) {
    $habitat_id = intval($_GET['habitat_id']);

    $sql = "SELECT id, nom, puissance_watts FROM equipement WHERE habitat_id = $habitat_id";
    $result = $conn->query($sql);

    $equipements = array();
    if ($result && $result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $equipements[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode($equipements);
} else {
    echo json_encode(["error" => "Il manque l'habitat_id frérot !"]);
}

$conn->close();
?>