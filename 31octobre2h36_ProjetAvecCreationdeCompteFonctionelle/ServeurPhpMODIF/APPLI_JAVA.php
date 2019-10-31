s<?php
header("Content-type:application/json");
$data = json_decode($_POST["data"]);
//logique avec data
/*
{ success: "vous etes connecté" }

{ error: "explication de l'erreur" }
*/
$retour = array('success' => "ce message est parvenu du fake serveur java d'autentification \o/" );
echo json_encode($data);
?>