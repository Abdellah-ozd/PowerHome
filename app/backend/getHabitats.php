<?php
// Les clés de la maison
$host = "127.0.0.1"; // On met l'IP direct, c'est plus sûr
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307; // 🔥 LA CLÉ DU BRAQUAGE EST LÀ 🔥

// On tente de forcer la serrure sur la bonne porte
$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    die("Aïe, la connexion a pété : " . $conn->connect_error);
}

// 🔥 LA NOUVELLE REQUÊTE QUI ASPIRE TOUT 🔥
$sql = "SELECT id, floor, area, resident_name, appliances_count FROM habitat";
$result = $conn->query($sql);

$habitats = array();

if ($result->num_rows > 0) {
    // On met tout dans les cartons
    while($row = $result->fetch_assoc()) {
        $habitats[] = $row;
    }
}

// On prévient Android qu'on envoie un colis de type JSON
header('Content-Type: application/json');
echo json_encode($habitats);

// On ferme la porte derrière nous
$conn->close();
?>