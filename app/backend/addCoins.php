<?php
header('Content-Type: application/json');
$host = "127.0.0.1";
$user = "root";
$password = "";
$dbname = "powerhome_db";
$port = 3307;

$conn = new mysqli($host, $user, $password, $dbname, $port);
if ($conn->connect_error) die(json_encode(["status" => "error"]));

$hab_id = isset($_GET['habitat_id']) ? intval($_GET['habitat_id']) : 1;

$sql = "UPDATE habitat SET eco_coins = eco_coins + 50 WHERE id = $hab_id";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
$conn->close();
?>