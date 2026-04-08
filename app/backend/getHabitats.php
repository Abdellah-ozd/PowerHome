<?php
$host = "127.0.0.1"; // On met l'IP direct, c'est plus sûr
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307;

$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    die("Aïe, la connexion a pété : " . $conn->connect_error);
}

$sql = "SELECT id, floor, area, resident_name, appliances_count FROM habitat";
$result = $conn->query($sql);

$habitats = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $habitats[] = $row;
    }
}

header('Content-Type: application/json');
echo json_encode($habitats);

$conn->close();
?>