<?php
header("Content-type:application/json; utf-8");
include("api.php");
$api = new api();
$data = json_decode(file_get_contents('php://input'), true);

if( isset($data) )
{
    if(isset($data['action']))
    {
                if( isset($data['login']) && isset($data['password'])&& isset($data['confirmPassword']) )
                    echo $api->create($data['login'], $data['password'],$data['confirmPassword']);
                else
                    echo $api->error("Données de creation non fournis");


        }
    }
    else
    {
        echo $api->error("Aucune action choisie.");
    }

?>