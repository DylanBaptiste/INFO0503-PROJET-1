<?php
header("Content-type:application/json");
$tableau = [ "code" => "erreur", "message" => "loginincorrect"];
echo json_encode($tableau);
?>