<?php
header("Content-type:application/json; utf-8");

include("api.php");
$api = new api();

$data = json_decode(file_get_contents('php://input'), true);

//n'affiche pas les erreurs
//error_reporting(0);
//ini_set('display_errors', 0);

/*
===== FORMAT DES REQUETE ====

actuellement:
{
    "action": 1,
    "identifiant": "toto",
    "mdp": "toto"
}

====== CODE DES ACTIONS ====== 

    1-  login
    2-  ...
*/


if( isset($data) )
{
    if(isset($data['action']))
    {
        switch ($data['action']) {
            case "1":
                if( isset($data['login']) && isset($data['password']) )
                    echo $api->login($data['login'], $data['password']);
                else
                    echo $api->error("Données de connexion non fournis");
                break;

            case "2":
                echo $api->error("Action pas implémenté");
                break;

            default:
                echo $api->error("Cette action n'existe pas");
                break;
        }
    }
    else
    {
        echo $api->error("Aucune action choisie.");
    }

}
else
{
    echo $api->error('Aucune données envoyées');
}



?>