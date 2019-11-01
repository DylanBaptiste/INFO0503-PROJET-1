<?php
include("api.php");

$json = "{\"administrateur\": \"toto\",\"titre\": \"le titre de ces morts\",\"sondageData\": {\"question0\": {\"repohgrfdjdnse1\": [\"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}, \"question1\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]},\"question2\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}}}";
$json1 = "{\"administrateur\": \"titi\",\"titre\": \"le titre de ces morts\",\"sondageData\": {\"question0\": {\"repohgrfdjdnse1\": [\"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}, \"question1\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]},\"question2\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}}}";
$json2 = '{"titre":"titre","sondageData":[{"question 2":{"reponse 4":["toto"],"reponse 3":[]}},{"question 1":{"reponse 2":["toto","titi"],"reponse 1":["titi","tata"]}}],"action":3,"login":"login"}';

$decode2 = json_decode($json2, true);
print_r($decode2);

$api = new api();

$api->createSondage($decode2["login"], $decode2["titre"], $decode2["sondageData"]);
?> 