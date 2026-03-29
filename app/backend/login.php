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
    die(json_encode(["status" => "error", "message" => "Le videur a fait un AVC : " . $conn->connect_error]));
}

if(isset($_GET['email']) && isset($_GET['password'])) {
    
    $email = $_GET['email'];
    $mdp = $_GET['password'];

    // On cherche le bracelet VIP dans la table user
    $sql = "SELECT token FROM user WHERE email='$email' AND password='$mdp'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        http_response_code(200);
        // On renvoie le pass VIP à Android !
        echo json_encode(["status" => "success", "token" => $row['token']]);
    } else {
        http_response_code(401); 
        echo json_encode(["status" => "error", "message" => "Identifiants foireux mon gars."]);
    }

} else {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Il manque l'email ou le mdp frérot !"]);
}

$conn->close();
?>