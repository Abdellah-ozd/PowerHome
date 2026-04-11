<?php
$host = "127.0.0.1";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307; 

$conn = new mysqli($host, $user, $password, $dbname, $port);

if ($conn->connect_error) {
    http_response_code(500);
    die(json_encode(["status" => "error", "message" => "Erreur serveur : " . $conn->connect_error]));
}

if(isset($_GET['email']) && isset($_GET['password'])) {

    $email = $conn->real_escape_string($_GET['email']);
    $mdp = $conn->real_escape_string($_GET['password']);

    $sql = "SELECT id, habitat_id FROM user WHERE email='$email' AND password='$mdp'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        http_response_code(200);

        echo json_encode([
            "status" => "success",
            "user_id" => $row['id'],
            "habitat_id" => $row['habitat_id']
        ]);
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