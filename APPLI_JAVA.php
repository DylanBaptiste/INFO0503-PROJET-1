<?php
header("Content-type:application/json");
$data = json_decode($_POST["data"]);
//logique avec data
/*
{ success: "vous etes connecté" }

{ error: "explication de l'erreur" }
*/
echo json_encode($data);
?>