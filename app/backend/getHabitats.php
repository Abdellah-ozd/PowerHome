<?php
$host = "localhost";
$user = "root";
$password = "";
$dbname = "powerhome_db";

// Connexion
$conn = new mysqli($host, $user, $password, $dbname);

if ($conn->connect_error) {
    die("Aïe : " . $conn->connect_error);
}

// On aspire tout, même les nouvelles colonnes
$sql = "SELECT id, floor, area, resident_name, appliances_count FROM habitat";
$result = $conn->query($sql);

$habitats = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $habitats[] = $row;
    }
}

// On balance le JSON
header('Content-Type: application/json');
echo json_encode($habitats);

$conn->close();
?>