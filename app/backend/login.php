<?php
$host = "127.0.0.1";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307; 

// Connexion à la base
$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    http_response_code(500);
    die(json_encode(["status" => "error", "message" => "Erreur serveur : " . $conn->connect_error]));
}

if(isset($_GET['email']) && isset($_GET['password'])) {

    // On sécurise un peu les entrées
    $email = $conn->real_escape_string($_GET['email']);
    $mdp = $conn->real_escape_string($_GET['password']);

    // 🎯 LA MODIF EST LÀ : On cherche l'habitat_id, pas le token !
    $sql = "SELECT habitat_id FROM user WHERE email='$email' AND password='$mdp'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        http_response_code(200);

        // On renvoie l'ID au téléphone Android pour qu'il le note sur son "post-it"
        echo json_encode(["status" => "success", "habitat_id" => $row['habitat_id']]);
    } else {
        http_response_code(401); 
        echo json_encode(["status" => "error", "message" => "Identifiants incorrects"]);
    }

} else {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Identifiants manquants"]);
}

$conn->close();
?>