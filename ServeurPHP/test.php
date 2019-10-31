<?php
include("api.php");

$json = "{\"administrateur\": \"toto\",\"titre\": \"le titre de ces morts\",\"sondageData\": {\"question0\": {\"repohgrfdjdnse1\": [\"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}, \"question1\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]},\"question2\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}}}";
$json2 = "{\"administrateur\": \"titi\",\"titre\": \"le titre de ces morts\",\"sondageData\": {\"question0\": {\"repohgrfdjdnse1\": [\"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}, \"question1\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]},\"question2\": {\"reponse1\": [\"toto1\", \"toto2\"],\"reponse1\": [\"toto1\", \"toto2\"]}}}";

$decode = json_decode($json, true);
$decode2 = json_decode($json2, true);

print_r($decode["sondageData"]);
echo "</br></br>";
$s = new Sondage($decode["administrateur"], $decode["titre"], $decode["sondageData"]);
print_r($s->toJSON());
/*echo "</br></br>";
echo $s->nbQuestions();
echo "</br></br>";
echo $s->nbVoteReponseByIndex(0, 0);*/
$api = new api();

$api->createSondage($decode["administrateur"], $decode["titre"], $decode["sondageData"]);
$api->createSondage($decode2["administrateur"], $decode2["titre"], $decode2["sondageData"]);
?> 