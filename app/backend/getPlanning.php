<?php
header('Content-Type: application/json');
$conn = new mysqli("127.0.0.1", "root", "", "powerhome_db", 3307);

$date_choisie = isset($_GET['date']) ? $conn->real_escape_string($_GET['date']) : date('Y-m-d');
$jour_semaine = date('N', strtotime($date_choisie)); 

$heures = [17, 18, 19, 20, 21, 22];
$planning = array();

foreach ($heures as $h) {
    $charge_de_base = 15 + (($h * $jour_semaine * 7) % 65); 
    
    $sql = "SELECT SUM(puissance_watts) as total_watts FROM reservation WHERE date_res = '$date_choisie' AND heure_res = $h";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    
    $watts_reserves = $row['total_watts'] ? $row['total_watts'] : 0;

    $impact_pourcent = ceil($watts_reserves / 100);
    
    $charge_totale = $charge_de_base + $impact_pourcent;
    if ($charge_totale > 100) $charge_totale = 100;

    $planning[] = array("heure" => $h, "charge" => $charge_totale);
}

echo json_encode($planning);
$conn->close();
?>